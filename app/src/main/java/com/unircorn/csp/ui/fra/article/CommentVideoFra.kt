package com.unircorn.csp.ui.fra.article

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import cn.jzvd.JZDataSource
import cn.jzvd.Jzvd
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItems
import com.blankj.utilcode.util.ToastUtils
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.hjq.bar.OnTitleBarListener
import com.kaopiz.kprogresshud.KProgressHUD
import com.rxjava.rxlife.lifeOnMain
import com.unircorn.csp.app.*
import com.unircorn.csp.app.helper.FileUtils2
import com.unircorn.csp.app.third.JZMediaIjk
import com.unircorn.csp.data.model.Article
import com.unircorn.csp.data.model.Attachment
import com.unircorn.csp.data.model.Comment
import com.unircorn.csp.data.model.CreateCommentParam
import com.unircorn.csp.data.model.base.Page
import com.unircorn.csp.data.model.base.Response
import com.unircorn.csp.databinding.FraCommentVideoBinding
import com.unircorn.csp.ui.adapter.CommentAdapter
import com.unircorn.csp.ui.base.PageFra
import com.unircorn.csp.ui.header.WebViewHeaderView
import io.reactivex.rxjava3.core.Single
import rxhttp.RxHttp
import rxhttp.wrapper.exception.HttpStatusCodeException
import java.io.File

class CommentVideoFra : PageFra<Comment>() {

    override fun initViews() {
        super.initViews()
        binding.titleBar.title = article.title
        initVideo()
    }

    override fun initBindings() {
        super.initBindings()
        if (article.attachments.isNotEmpty()) {
            binding.titleBar.rightTitle = "附件"
            binding.titleBar.setOnTitleBarListener(object : OnTitleBarListener {
                override fun onLeftClick(v: View?) {
                    requireActivity().finish()
                }

                override fun onRightClick(v: View?) {
                    showAttachmentDialog()
                }

                override fun onTitleClick(v: View?) {
                }
            })
        }
        binding.btnCreateComment.safeClicks().subscribe { createCommentX() }
    }

    @SuppressLint("CheckResult")
    private fun showAttachmentDialog() {
        MaterialDialog(requireContext()).show {
            listItems(items = article.attachments.map { it.filename }) { _, index, _ ->
                downloadAttachment(article.attachments[index])
            }
        }
    }

    private fun downloadAttachment(attachment: Attachment) = with(attachment) {
        if (exists) {
            FileUtils2.openFile(requireContext(), file = file)
            return@with
        }
        val progressMask = KProgressHUD.create(requireContext())
            .setStyle(KProgressHUD.Style.BAR_DETERMINATE)
            .setCancellable(true)
            .setDimAmount(0.5f)
            .setMaxProgress(100)
            .show()
        val fullUrl = baseUrl + url
        RxHttp.get(fullUrl)
            .addHeader(Cookie, "${SESSION}=${Globals.session}")
            .asDownload(
                path,
            ) { progressMask.setProgress(it.progress) }
            .subscribe(
                {
                    progressMask.dismiss()
                    FileUtils2.openFile(requireContext(), file = File(it))
                },
                {
                    progressMask.dismiss()
                    if (it is HttpStatusCodeException) {
                        if (it.statusCode == "401")
                            ToastUtils.showLong("登陆超时，请重新登陆")
                    }
                }
            )
    }

    private fun initVideo() = with(binding) {
//        val url = "http://8.136.101.204" + "/v/饺子主动.mp4"
        val jzDataSource = JZDataSource(article.video.fullUrl, article.title)
        jzDataSource.headerMap[Cookie] = "$SESSION=${Globals.session}"
        binding.jzvdStd.setUp(jzDataSource, Jzvd.SCREEN_NORMAL, JZMediaIjk::class.java)
    }

    private fun getArticle() {
        api.getArticle(objectId = article.objectId)
            .lifeOnMain(this)
            .subscribe(
                {
                    if (it.failed) return@subscribe
                    pageAdapter.addHeaderView(WebViewHeaderView(content = it.data.content))

                },
                { it.toast() }
            )
    }

    private fun createCommentX() {
        if (binding.etContent.isEmpty()) {
            ToastUtils.showShort("评论不能为空")
            return
        }
        createComment()
    }

    private fun createComment() {
        api.createComment(
            articleId = article.objectId,
            createCommentParam = CreateCommentParam(content = binding.etContent.trimText())
        )
            .lifeOnMain(this)
            .subscribe(
                {
                    if (it.failed) return@subscribe
                    binding.etContent.setText("")
                    loadStartPage()
                },
                { it.toast() }
            )
    }

    override fun initPageAdapter() {
        pageAdapter = CommentAdapter()
        pageAdapter.headerWithEmptyEnable = true
        getArticle()
    }

    override fun initItemDecoration(recyclerView: RecyclerView) {
        recyclerView.apply {
            MaterialDividerItemDecoration(
                requireContext(),
                LinearLayoutManager.VERTICAL
            ).apply {
                dividerThickness = 1
            }.let { addItemDecoration(it) }
        }
    }

    override fun loadPage(page: Int): Single<Response<Page<Comment>>> =
        api.getComment(articleId = article.objectId, page = page)

    private val article by lazy { requireArguments().getSerializable(Param) as Article }

    override val mRecyclerView: RecyclerView
        get() = binding.recyclerView

    override val mSwipeRefreshLayout: SwipeRefreshLayout
        get() = binding.swipeRefreshLayout

// ----

    private var _binding: FraCommentVideoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FraCommentVideoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}