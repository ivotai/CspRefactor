package com.unircorn.csp.data.model

import com.blankj.utilcode.util.FileUtils
import com.unircorn.csp.app.MyComponent
import com.unircorn.csp.app.baseUrl
import java.io.File
import java.io.Serializable

data class Attachment(
    val attachmentId: String,
    val filename: String,
    val url: String,
    val imageUrl: String?
) : Serializable {
    private val extension get() = FileUtils.getFileExtension(filename)
    private val uniqueFilename get() = "$attachmentId.$extension"
    val path get() = "${MyComponent().context.filesDir}/$uniqueFilename"
    val file get() = File(path)
    val exists get() = file.exists()
    val fullUrl get() = baseUrl + url
}