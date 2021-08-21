package com.unircorn.csp.app.helper

import com.blankj.utilcode.util.AppUtils
import com.kaopiz.kprogresshud.KProgressHUD
import com.rxjava.rxlife.lifeOnMain
import com.unircorn.csp.app.MyComponent
import com.unircorn.csp.app.startAct
import com.unircorn.csp.app.toast
import com.unircorn.csp.ui.act.LoginAct
import com.unircorn.csp.ui.act.MainAct
import rxhttp.RxHttp
import java.io.File

object VersionHelper {

    fun check(loginAct: LoginAct) {
        MyComponent().api.checkUpdate(version = AppUtils.getAppVersionName())
            .lifeOnMain(loginAct)
            .subscribe(
                {
                    if (it.newVersion)
                        download(loginAct = loginAct, apkUrl = it.apkUrl)
                    else {
                        loginAct.startAct(MainAct::class.java)
                        loginAct.finish()
                    }
                },
                { it.toast() }
            )
    }

    private fun download(loginAct: LoginAct, apkUrl: String) {
        val progressMask = KProgressHUD.create(loginAct)
            .setStyle(KProgressHUD.Style.BAR_DETERMINATE)
            .setCancellable(true)
            .setDimAmount(0.5f)
            .setMaxProgress(100)
            .show()
        RxHttp.get(apkUrl).asDownload(
            "${MyComponent().context.filesDir.path}/csp.apk"
        ) {
            progressMask.setProgress(it.progress)
        }.subscribe(
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