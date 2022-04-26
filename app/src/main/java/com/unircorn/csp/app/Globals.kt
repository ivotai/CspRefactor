package com.unircorn.csp.app

import com.unircorn.csp.data.model.LoginResponse
import com.unircorn.csp.data.model.User

object Globals {

    val session: String get() = loginResponse.session
    val token: String get() = loginResponse.loginToken
    val user: User get() = loginResponse.user

    lateinit var loginResponse: LoginResponse

    var isLogout = false

    val department: String get() = user.department

}