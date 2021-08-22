package com.unircorn.csp.ui.act

import cn.jzvd.Jzvd
import com.unircorn.csp.ui.base.BaseAct
import com.unircorn.csp.ui.fra.MainFra

class MainAct : BaseAct() {

    override fun createFragment() = MainFra()

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