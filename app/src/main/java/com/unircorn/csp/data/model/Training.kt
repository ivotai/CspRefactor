package com.unircorn.csp.data.model

data class Training(
    val name: String,
    val classHour: Double,
    val passed: Int,
    val completed: Int,
    val objectId: String
) {
    val isCompleted
        get() = if (completed == 0) "未完成" else "已完成"
}