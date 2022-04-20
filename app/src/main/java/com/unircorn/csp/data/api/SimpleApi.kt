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

    // media play

    @POST("api/v1/app/article/{articleId}/media-play")
    fun createMediaPlay(
        @Path("articleId") articleId: String,
        @Body any: Any = Any()
    ): Single<Response<MediaPlayResponse>>

    @POST("api/v1/app/article/{articleId}/media-play")
    fun mediaPlayStatus(
        @Path("articleId") articleId: String,
        @Body mediaPlayStatus: MediaPlayStatus
    ): Single<Response<MediaPlayResponse>>

    @GET("api/v1/app/article/media-play/summary")
    fun mediaPlaySummary(
        @Query("timeUnit") timeUnit: String,
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String
    ): Single<Response<MediaPlaySummaryResponse>>

    @GET("api/v1/app/code")
    fun getCode(@Query("tag") tag: String): Single<Response<List<Code>>>

    //

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

    @POST("api/v1/app/topic")
    fun createTopic(@Body createTopicParam: CreateTopicParam): Single<Response<Any>>

    @GET("api/v1/app/topic")
    fun getTopic(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int = defaultPageSize,
        @Query("type") type: Int
    ): Single<Response<Page<Topic>>>

    @GET("api/v1/app/topic")
    fun getMyTopic(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int = defaultPageSize,
        @Query("self") self: Int = 1
    ): Single<Response<Page<Topic>>>

    @DELETE("api/v1/app/topic/{topicId}")
    fun deleteTopic(
        @Path("topicId") topicId: String,
    ): Single<Response<Any>>

    @GET("api/v1/app/topic/{topicId}/comment")
    fun getCommentT(
        @Path("topicId") topicId: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int = defaultPageSize
    ): Single<Response<Page<Comment>>>

    @POST("api/v1/app/topic/{topicId}/comment")
    fun createCommentT(
        @Path("topicId") topicId: String,
        @Body createCommentParam: CreateCommentParam
    ): Single<Response<Any>>

    @POST("api/v1/app/examination?test=1")
    fun createExaminationJustStudy(): Single<Response<Examination>>

    @POST("api/v1/app/examination")
    fun createExamination(): Single<Response<Examination>>

    @POST("api/v1/app/examination/{examinationId}")
    fun submitExamination(
        @Path("examinationId") examinationId: String,
        @Body submitExaminationParam: SubmitExaminationParam
    ): Single<Response<ExaminationResult>>

}