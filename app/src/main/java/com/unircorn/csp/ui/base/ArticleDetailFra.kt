package com.unircorn.csp.ui.base

import android.annotation.SuppressLint
import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItems
import com.blankj.utilcode.util.ToastUtils
import com.hjq.bar.OnTitleBarListener
import com.hjq.bar.TitleBar
import com.rxjava.rxlife.lifeOnMain
import com.unircorn.csp.app.*
import com.unircorn.csp.app.helper.FileUtils2
import com.unircorn.csp.app.helper.ProgressHelper
import com.unircorn.csp.data.model.Article
import com.unircorn.csp.data.model.Attachment
import com.unircorn.csp.data.model.Comment
import com.unircorn.csp.data.model.CreateCommentParam
import com.unircorn.csp.data.model.base.Page
import com.unircorn.csp.data.model.base.Response
import io.reactivex.rxjava3.core.Single
import rxhttp.RxHttp
import rxhttp.wrapper.exception.HttpStatusCodeException
import java.io.File

abstract class ArticleDetailFra : MediaOwnerFra() {

    override fun getMediaOwnerId(): String {
        return article.objectId
    }

    override fun initViews() {
        super.initViews()
        titleBar.title = article.title
    }

    override fun initBindings() {
        super.initBindings()
        if (article.hasAttachments) titleBar.rightTitle = "附件"
        titleBar.setOnTitleBarListener(object : OnTitleBarListener {
            override fun onLeftClick(v: View?) {
                finishAct()
            }

            override fun onRightClick(v: View?) {
                if (article.hasAttachments)
                    showAttachmentsDialog()
            }

            override fun onTitleClick(v: View?) {
            }
        })
    }

    @SuppressLint("CheckResult")
    private fun showAttachmentsDialog() {
        MaterialDialog(requireContext()).show {
            listItems(items = article.attachments.map { it.filename }) { _, index, _ ->
                openAttachment(article.attachments[index])
            }
        }
    }

    private fun openAttachment(attachment: Attachment) = with(attachment) {
        if (exists) {
            FileUtils2.openFile(requireContext(), file = file)
            return@with
        }
        val progressMask = ProgressHelper.showMask(requireActivity())
        RxHttp.get(fullUrl)
            .addHeader(Cookie, "$SESSION=${Globals.session}")
            .asDownload(path) { progressMask.setProgress(it.progress) }
            .subscribe(
                {
                    progressMask.dismiss()
                    FileUtils2.openFile(requireContext(), file = File(it))
                },
                {
                    progressMask.dismiss()
                    if (it is HttpStatusCodeException) {
                        // todo 如果超时，重新登录后下载
                        if (it.statusCode == "401")
                            ToastUtils.showLong("登陆超时，请重新登陆")
                    }
                }
            )
    }

    override fun createComment() {
        api.createComment(
            articleId = article.objectId,
            createCommentParam = CreateCommentParam(content = etContent.trimText())
        ).lifeOnMain(this).subscribe(
            {
                if (it.failed) return@subscribe
                afterCommentCreated()
            },
            { it.errorMsg().toast() }
        )
    }

    override fun loadPage(page: Int): Single<Response<Page<Comment>>> =
        api.getComment(articleId = article.objectId, page = page)

    protected val article by lazy { requireArguments().getSerializable(Param) as Article }

    abstract val titleBar: TitleBar


}