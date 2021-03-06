package com.unircorn.csp.app.module

import com.unircorn.csp.data.api.SimpleApi
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {

    single {

        get<Retrofit>().create(SimpleApi::class.java)

    }

}