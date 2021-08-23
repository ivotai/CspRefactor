package com.unircorn.csp.ui.base

import com.blankj.utilcode.util.ToastUtils
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.unircorn.csp.app.isEmpty
import com.unircorn.csp.app.safeClicks
import com.unircorn.csp.data.model.Comment
import com.unircorn.csp.ui.adapter.CommentAdapter
import com.unircorn.csp.ui.base.PageFra

abstract class CommentPageFra : PageFra<Comment>() {

    override fun initBindings() {
        super.initBindings()
        btnCreateComment.safeClicks().subscribe { createCommentX() }
    }

    override fun initPageAdapter() {
        pageAdapter = CommentAdapter()
    }

    private fun createCommentX() {
        if (etContent.isEmpty()) {
            ToastUtils.showShort("评论不能为空")
            return
        }
        createComment()
    }

    protected fun afterCommentCreated() {
        etContent.setText("")
        loadStartPage()
    }

    abstract fun createComment()

    abstract val etContent: TextInputEditText

    abstract val btnCreateComment: MaterialButton

}