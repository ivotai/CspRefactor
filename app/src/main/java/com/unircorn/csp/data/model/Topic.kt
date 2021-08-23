package com.unircorn.csp.data.model

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.unircorn.csp.ui.act.topic.TopicDetailNormalAct
import com.unircorn.csp.ui.act.topic.TopicDetailVideoAct
import java.io.Serializable

data class Topic(
    val issueTime: Long,
    val issuer: String,
    val objectId: String,
    val commentCount: Int,
    val content: String,
    val title: String,
    val images: List<Attachment>,
    val videos: List<Attachment>
) : Serializable, MultiItemEntity {

    companion object {
        const val topic_normal = 0
        const val topic_image = 1
        const val topic_video = 2
    }

    override val itemType: Int
        get() = when {
            videos.isNotEmpty() -> topic_video
            images.isNotEmpty() -> topic_image
            else -> topic_normal
        }

    val targetClass: Class<*>
        get() = when (itemType) {
            topic_video -> TopicDetailVideoAct::class.java
            topic_image -> TopicDetailNormalAct::class.java // todo
            else -> TopicDetailNormalAct::class.java
        }

}

data class CreateTopicParam(
    var content: String = "",
    var images: List<UploadResponse> = ArrayList(),
    var title: String = "",
    var videos: List<UploadResponse> = ArrayList()
)