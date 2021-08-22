package com.unircorn.csp.data.model

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.unircorn.csp.ui.act.topic.CommentTopicNormalAct
import com.unircorn.csp.ui.act.topic.CommentTopicVideoAct
import com.unircorn.csp.ui.adapter.TopicAdapter
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
) : Serializable {
    val targetClass: Class<*>
        get() = when {
            videos.isNotEmpty() -> CommentTopicVideoAct::class.java
            // todo
//        images.isNotEmpty() ->3
            else -> CommentTopicNormalAct::class.java
        }
}

data class CreateTopicParam(
    var content: String = "",
    var images: List<UploadResponse> = ArrayList(),
    var title: String = "",
    var videos: List<UploadResponse> = ArrayList()
)

class TopicNormal(val topic: Topic) : MultiItemEntity {
    override val itemType = TopicAdapter.topic_normal
}

class TopicVideo(val topic: Topic) : MultiItemEntity {
    override val itemType = TopicAdapter.topic_video
}