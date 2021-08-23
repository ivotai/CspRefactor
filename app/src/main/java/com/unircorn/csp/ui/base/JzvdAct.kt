package com.unircorn.csp.ui.base

import cn.jzvd.Jzvd

abstract class JzvdAct : BaseAct() {

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