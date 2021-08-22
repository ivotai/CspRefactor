package com.unircorn.csp.data.model

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.unircorn.csp.ui.adapter.TopicAdapter
import java.io.Serializable

data class Topic(
    val issueTime: Long,
    val issuer: String,
    val objectId: String,
    val commentCount: Int,
    val content: String,
    val title: String,
    val images: List<UploadResponse>,
    val videos: List<UploadResponse>
) : Serializable {
    val type: Int
        get() = when {
            videos.isNotEmpty() -> 3
            // todo
//        images.isNotEmpty() ->3
            else -> 0
        }
}

data class CreateTopicParam(
    val content: String,
    val images: List<UploadResponse> = ArrayList(),
    val title: String,
    val videos: List<UploadResponse> = ArrayList()
)

class TopicNormal(val topic: Topic) : MultiItemEntity {
    override val itemType = TopicAdapter.topic_normal
}

class TopicVideo(val topic: Topic) : MultiItemEntity {
    override val itemType = TopicAdapter.topic_video
}