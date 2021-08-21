package com.unircorn.csp.data.model

data class CheckUpdateResponse(
    val apkUrl:String,
    val versionNumber:String,
    val newVersion:Boolean
)