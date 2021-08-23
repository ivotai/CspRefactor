package com.unircorn.csp.app.helper

import androidx.appcompat.app.AppCompatActivity
import com.kaopiz.kprogresshud.KProgressHUD

object ProgressHelper {

    fun showMask(appCompatActivity: AppCompatActivity) = KProgressHUD.create(appCompatActivity)
        .setStyle(KProgressHUD.Style.BAR_DETERMINATE)
        .setCancellable(false)
        .setDimAmount(0.5f)
        .setMaxProgress(100)
        .show()

}