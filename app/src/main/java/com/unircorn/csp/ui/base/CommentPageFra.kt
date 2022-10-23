package com.unircorn.csp.ui.base

import com.blankj.utilcode.util.ToastUtils
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.utils.sizeDp
import com.unicorn.sanre.icon.Fas
import com.unircorn.csp.app.isEmpty
import com.unircorn.csp.app.safeClicks
import com.unircorn.csp.data.model.Comment
import com.unircorn.csp.ui.adapter.CommentAdapter

abstract class CommentPageFra : PageFra<Comment>() {

    override fun initViews() {
        super.initViews()
        initFloatingActionButton()
    }

    override fun initBindings() {
        super.initBindings()
        btnCreateComment.safeClicks().subscribe { createCommentX() }
        floatingActionButton.safeClicks().subscribe { like() }
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

    open fun like(){

    }

    private fun initFloatingActionButton() {
        floatingActionButton.setImageDrawable(
            IconicsDrawable(requireContext(), Fas.Icon.fas_thumbs_up).apply {
                sizeDp = 24
            }
        )
    }

    abstract fun createComment()

    abstract val etContent: TextInputEditText

    abstract val btnCreateComment: MaterialButton

    abstract val floatingActionButton: FloatingActionButton

}