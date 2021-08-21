package com.unircorn.csp.data.model

import com.blankj.utilcode.util.FileUtils
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.unircorn.csp.app.MyComponent
import java.io.File
import java.io.Serializable

data class Article(
    val attachments: List<Attachment>,
    val category: String,
    val content: String,
    val objectId: String,
    val publishTime: Long,
    val publisher: String,
    val title: String,
    val cover: String,
    val type: Int,          // 1=图文，2=图文+视频，3=pdf
    val pdf: Attachment,
    val video: Attachment
) : Serializable

data class Attachment(
    val attachmentId: String,
    val filename: String,
    val url: String
) : Serializable {
    private val extension get() = FileUtils.getFileExtension(filename)
    private val uniqueFilename get() = "$attachmentId.$extension"
    val path get() = "${MyComponent().context.filesDir}/$uniqueFilename"
    val file get() = File(path)
    val exists get() = file.exists()
//    val fullUrl get() = MyComponent().baseUrl + url
}

const val normal = 0
const val withImage = 1

class ArticleWithImage(val article: Article) : MultiItemEntity {
    override val itemType = withImage
}

class ArticleNormal(val article: Article) : MultiItemEntity {
    override val itemType = normal
}