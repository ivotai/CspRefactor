package com.unircorn.csp.data.model

import com.blankj.utilcode.util.ToastUtils
import com.unircorn.csp.app.toast

// LoginResponse 比较特别，不继承自 Response。
data class LoginResponse(
    val loginToken: String,
    val session: String,
    val success: Boolean,
    val user: User,
    val message: String
) {
    val failed: Boolean
        get() {
            val failed = !success
            if (failed) message.toast()
            return failed
        }
}


data class User(
    val id: Long,
    val roleTag: String,
    val roles: List<String>,
    val username: String,
    val courtName: String
)