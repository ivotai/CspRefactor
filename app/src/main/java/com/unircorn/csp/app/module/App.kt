package com.unircorn.csp.app.module

import com.unircorn.csp.app.baseUrl
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {

    single(named(baseUrl)) { "https://www.baidu.com/" }

}
