package com.unircorn.csp.ui.act.article

import com.unircorn.csp.ui.base.BaseAct
import com.unircorn.csp.ui.fra.article.CommentNormalFra

class CommentAct : BaseAct() {

    override fun createFragment() = CommentNormalFra()

}