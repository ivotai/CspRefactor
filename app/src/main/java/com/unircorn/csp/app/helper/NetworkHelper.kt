package com.unircorn.csp.app.helper

import com.unircorn.csp.app.Cookie
import com.unircorn.csp.app.Globals
import com.unircorn.csp.app.MyComponent
import com.unircorn.csp.app.SESSION
import okhttp3.Interceptor
import okhttp3.Response

object NetworkHelper {

    fun proceedRequestWithNewSession(chain: Interceptor.Chain): Response {
        // session 过期时使用 token 登录，获取新的 session 和 token。
        MyComponent().api.loginSilently().execute().body().let { Globals.loginResponse = it!! }
        return proceedRequestWithSession(chain)
    }

    fun proceedRequestWithSession(chain: Interceptor.Chain): Response {
        return chain.request().newBuilder()
            .removeHeader(Cookie)
            .addHeader(Cookie, "${SESSION}=${Globals.session}")
            .build()
            .let { chain.proceed(it) }
    }

}