package com.unircorn.csp.ui.act.topic

import cn.jzvd.Jzvd
import com.unircorn.csp.ui.base.BaseAct
import com.unircorn.csp.ui.fra.topic.CommentTopicVideoFra

class CommentTopicVideoAct : BaseAct() {

    override fun createFragment() = CommentTopicVideoFra()

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