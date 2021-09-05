package com.unircorn.csp.app.helper

import androidx.fragment.app.FragmentActivity
import com.blankj.utilcode.util.AppUtils
import com.rxjava.rxlife.lifeOnMain
import com.unircorn.csp.app.MyComponent
import com.unircorn.csp.app.errorMsg
import com.unircorn.csp.app.startAct
import com.unircorn.csp.app.toast
import com.unircorn.csp.ui.act.MainAct2
import rxhttp.RxHttp
import java.io.File

object VersionHelper {

    fun checkVersion(fragmentActivity: FragmentActivity) {
        MyComponent().api.checkUpdate(version = AppUtils.getAppVersionName())
            .lifeOnMain(fragmentActivity)
            .subscribe(
                {
                    if (it.newVersion)
                        downloadApk(fragmentActivity = fragmentActivity, apkUrl = it.apkUrl)
                    else
                        fragmentActivity.startAct(targetClass = MainAct2::class.java, finishSelf = true)
                },
                { it.errorMsg().toast() }
            )
    }

    private fun downloadApk(fragmentActivity: FragmentActivity, apkUrl: String) {
        val progressMask = ProgressHelper.showMask(fragmentActivity)
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