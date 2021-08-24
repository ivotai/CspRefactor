package com.unircorn.csp.app.module

import com.unircorn.csp.app.baseUrl
import com.unircorn.csp.app.helper.NetworkHelper
import com.unircorn.csp.app.timeout
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rxhttp.RxHttpPlugins
import java.util.concurrent.TimeUnit

val networkModule = module {

    single {

        OkHttpClient.Builder()
//            .readTimeout(timeout, TimeUnit.SECONDS)
//            .connectTimeout(timeout, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                if ("login" in chain.request().url.encodedPathSegments || "version" in chain.request().url.encodedPathSegments)
                    chain.proceed(chain.request())
                else
                    NetworkHelper.proceedRequestWithSession(chain)
            }
            .addInterceptor { chain ->
                val response = chain.proceed(chain.request())
                if (response.code != 401) return@addInterceptor response
                // 401 表示 session 过期
                NetworkHelper.proceedRequestWithNewSession(chain)
            }
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

    }

    single {

        val client = OkHttpClient.Builder()
            .readTimeout(timeout, TimeUnit.SECONDS)
            .connectTimeout(timeout, TimeUnit.SECONDS)
            .writeTimeout(timeout,TimeUnit.SECONDS)
//            .addInterceptor(HttpLoggingInterceptor().apply {
//                level = HttpLoggingInterceptor.Level.BODY
//            })
            .build()

//        RxHttpPlugins.init(client)

        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(get())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

}
