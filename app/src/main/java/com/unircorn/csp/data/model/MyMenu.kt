package com.unircorn.csp.data.model

import com.blankj.utilcode.util.AppUtils

enum class MyMenu(val text: String) {

    Department("所在部门"),
    StudySummary("学习统计"),
    ChangePassword("修改密码"),
    ClearDownloads("清空已下载文件"),
    Logout("退出登录"),
    VersionName("版本号：${AppUtils.getAppVersionName()}")
    ;

    companion object {
        val all get() = listOf(Department, ChangePassword, ClearDownloads, Logout, VersionName)
    }

}

