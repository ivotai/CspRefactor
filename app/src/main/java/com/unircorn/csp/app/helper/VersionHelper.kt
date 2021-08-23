package com.unircorn.csp.app.helper

import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.AppUtils
import com.rxjava.rxlife.lifeOnMain
import com.unircorn.csp.app.MyComponent
import com.unircorn.csp.app.startAct
import com.unircorn.csp.app.toast
import com.unircorn.csp.ui.act.MainAct
import rxhttp.RxHttp
import java.io.File

object VersionHelper {

    fun checkVersion(appCompatActivity: AppCompatActivity) {
        MyComponent().api.checkUpdate(version = AppUtils.getAppVersionName())
            .lifeOnMain(appCompatActivity)
            .subscribe(
                {
                    if (it.newVersion)
                        downloadApk(appCompatActivity = appCompatActivity, apkUrl = it.apkUrl)
                    else
                        appCompatActivity.startAct(cls = MainAct::class.java, finishSelf = true)
                },
                { it.toast() }
            )
    }

    private fun downloadApk(appCompatActivity: AppCompatActivity, apkUrl: String) {
        val progressMask = ProgressHelper.showMask(appCompatActivity)
        RxHttp.get(apkUrl)
            .asDownload("${MyComponent().context.filesDir.path}/csp.apk")
            { progressMask.setProgress(it.progress) }
            .subscribe(
                {
                    progressMask.dismiss()
                    AppUtils.installApp(File(it))
                },
                {
                    progressMask.dismiss()
                }
            )
    }

}