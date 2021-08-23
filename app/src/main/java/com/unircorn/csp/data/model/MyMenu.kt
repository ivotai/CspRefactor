package com.unircorn.csp.data.model

import com.blankj.utilcode.util.AppUtils

enum class MyMenu(val text: String) {

    ChangePassword("修改密码"),
    ClearDownloads("清空已下载文件"),
    Logout("退出登录"),
    VersionName("版本号：${AppUtils.getAppVersionName()}")
    ;

    companion object {
        val all get() = listOf(ChangePassword, ClearDownloads, Logout, VersionName)
    }

}

