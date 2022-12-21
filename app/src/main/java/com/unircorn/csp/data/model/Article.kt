package com.unircorn.csp.data.model

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.unircorn.csp.ui.act.article.ArticleDetailNormalAct
import com.unircorn.csp.ui.act.article.ArticleDetailPdfAct
import com.unircorn.csp.ui.act.article.ArticleDetailVideoAct
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
    val video: Attachment,
    val readCount: Int,
    val commentCount: Int,
    val likeCount: Int,
    var liked: Int,
    val completed: Int?
) : Serializable, MultiItemEntity {

    val isLiked get() = liked == 1

    fun likeToggle() {
        liked = if (liked == 1) 0 else 1
    }

    companion object {
        const val article_normal = 0
        const val article_image = 1
    }

    val targetClass: Class<*>
        get() = when (type) {
            1 -> ArticleDetailNormalAct::class.java
            2 -> ArticleDetailVideoAct::class.java
            else -> ArticleDetailPdfAct::class.java
        }

    override val itemType: Int
        get() = if (cover.isEmpty()) article_normal else article_image

    val hasAttachments: Boolean get() = attachments.isNotEmpty()

}


