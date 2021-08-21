package com.unircorn.csp.data.api

import com.unircorn.csp.app.Globals
import com.unircorn.csp.data.model.CheckUpdateResponse
import com.unircorn.csp.data.model.LoginResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SimpleApi {

    @GET("login/account")
    fun login(
        @Query("username") username: String,
        @Query("password") password: String
    ): Single<LoginResponse>

    @GET("login/silence")
    fun loginSilently(@Query("token") token: String = Globals.token): Call<LoginResponse>

    @GET(value = "public/checkUpdate")
    fun checkUpdate(
        @Query("version") version: String,
        @Query("id") id: String = "1001"
    ): Single<CheckUpdateResponse>

}