package com.unircorn.csp.app.helper

import androidx.fragment.app.FragmentActivity
import com.kaopiz.kprogresshud.KProgressHUD

object ProgressHelper {

    fun showMask(fragmentActivity: FragmentActivity): KProgressHUD =
        KProgressHUD.create(fragmentActivity)
            .setStyle(KProgressHUD.Style.BAR_DETERMINATE)
            .setCancellable(false)
            .setDimAmount(0.5f)
            .setMaxProgress(100)
            .show()

}