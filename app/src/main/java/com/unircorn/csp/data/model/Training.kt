package com.unircorn.csp.data.model

data class Training(
    val name: String,
    val classHour: Double,
    val complete: Int
) {
    val isCompleted
        get() = if (complete == 0) "未完成" else "已完成"
}