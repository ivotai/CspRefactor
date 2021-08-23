package com.unircorn.csp.ui.base

import android.view.View
import com.hjq.bar.OnTitleBarListener
import com.hjq.bar.TitleBar
import com.rxjava.rxlife.lifeOnMain
import com.unircorn.csp.app.*
import com.unircorn.csp.data.model.*
import com.unircorn.csp.data.model.base.Page
import com.unircorn.csp.data.model.base.Response
import io.reactivex.rxjava3.core.Single

abstract class TopicDetailFra : CommentPageFra() {

    override fun initViews() {
        super.initViews()
        titleBar.title = topic.title
    }

    override fun initBindings() {
        super.initBindings()

        titleBar.setOnTitleBarListener(object : OnTitleBarListener {
            override fun onLeftClick(v: View?) {
                finishAct()
            }

            override fun onRightClick(v: View?) {

            }

            override fun onTitleClick(v: View?) {
            }
        })
    }


    override fun createComment() {
        api.createCommentT(
            topicId = topic.objectId,
            createCommentParam = CreateCommentParam(content = etContent.trimText())
        ).lifeOnMain(this).subscribe(
            {
                if (it.failed) return@subscribe
                afterCommentCreated()
            },
            { it.toast() }
        )
    }

    override fun loadPage(page: Int): Single<Response<Page<Comment>>> =
        api.getCommentT(topicId = topic.objectId, page = page)

    protected val topic by lazy { requireArguments().getSerializable(Param) as Topic }

    abstract val titleBar: TitleBar


}