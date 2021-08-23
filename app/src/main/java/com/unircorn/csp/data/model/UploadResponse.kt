package com.unircorn.csp.data.model

import java.io.Serializable

data class UploadResponse(
    val filename: String,
    val tempFilename: String,
    val link: String
) : Serializable