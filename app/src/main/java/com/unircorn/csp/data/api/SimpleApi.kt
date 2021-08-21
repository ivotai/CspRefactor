package com.unircorn.csp.data.api

import com.unircorn.csp.app.Globals
import com.unircorn.csp.app.defaultPageSize
import com.unircorn.csp.data.model.*
import com.unircorn.csp.data.model.base.Page
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

    @GET("api/v1/app/article")
    fun getArticle(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int = defaultPageSize,
        @Query("category") category: String = "",
        @Query("keyword") keyword: String = ""
    ): Single<Response<Page<Article>>>

    @GET("api/v1/app/article/{objectId}")
    fun getArticle(@Path("objectId") objectId: String): Single<Response<Article>>

    @GET("api/v1/app/article/{articleId}/comment")
    fun getComment(
        @Path("articleId") articleId: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int = defaultPageSize
    ): Single<Response<Page<Comment>>>

    @POST("api/v1/app/article/{articleId}/comment")
    fun createComment(
        @Path("articleId") articleId: String,
        @Body createCommentParam: CreateCommentParam
    ): Single<Response<Any>>

}