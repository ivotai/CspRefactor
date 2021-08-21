package com.unircorn.csp.app

import android.content.Context
import com.unircorn.csp.data.api.SimpleApi
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MyComponent : KoinComponent {

    val api: SimpleApi by inject()

    val context: Context by inject()

}