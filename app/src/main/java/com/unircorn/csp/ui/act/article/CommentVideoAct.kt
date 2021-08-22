package com.unircorn.csp.ui.act.article

import com.unircorn.csp.ui.base.BaseAct
import com.unircorn.csp.ui.fra.article.CommentVideoFra
import cn.jzvd.Jzvd

class CommentVideoAct : BaseAct() {

    override fun createFragment() = CommentVideoFra()

    override fun onBackPressed() {
        if (Jzvd.backPress()) {
            return
        }
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        Jzvd.releaseAllVideos()
    }

}