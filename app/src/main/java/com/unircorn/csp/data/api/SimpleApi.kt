package com.unircorn.csp.data.api

import com.unircorn.csp.app.Globals
import com.unircorn.csp.data.model.CheckUpdateResponse
import com.unircorn.csp.data.model.LoginResponse
import com.unircorn.csp.data.model.ModifyPasswordParam
import com.unircorn.csp.data.model.Summary
import com.unircorn.csp.data.model.base.Response
import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.http.*

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

    @GET("api/v1/app/i/summary")
    fun getSummary(): Single<Response<Summary>>

    @Headers("accept:application/json")
    @GET(value = "logout")
    fun logout(): Single<Response<Any>>

    @PUT("api/v1/app/modifyPassword")
    fun modifyPassword(@Body modifyPasswordParam: ModifyPasswordParam): Single<Response<Any>>

}