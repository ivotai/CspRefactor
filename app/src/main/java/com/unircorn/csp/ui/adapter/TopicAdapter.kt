package com.unircorn.csp.ui.adapter

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.unircorn.csp.R
import com.unircorn.csp.app.displayDateFormat
import com.unircorn.csp.data.model.Article
import com.unircorn.csp.data.model.ArticleNormal
import com.unircorn.csp.data.model.TopicNormal
import com.unircorn.csp.ui.act.article.CommentAct
import com.unircorn.csp.ui.act.article.CommentPdfAct
import com.unircorn.csp.ui.act.article.CommentVideoAct
import org.joda.time.DateTime
import java.util.*

class TopicAdapter : BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder>(ArrayList()),
    LoadMoreModule {

    companion object {
        const val topic_normal = 0
        const val topic_video = 3
    }

    init {
        addItemType(topic_normal, R.layout.item_topic_normal)
        addItemType(topic_video, R.layout.item_topic_normal)    // todo
    }

    override fun convert(holder: BaseViewHolder, item: MultiItemEntity) {
        when (item.itemType) {
            topic_normal -> {
                item as TopicNormal
                val topic = item.topic
                holder.setText(R.id.tvTitle, topic.title)
                holder.setText(
                    R.id.tvIssueTime,
                    DateTime(topic.issueTime).toString(displayDateFormat)
                )
                holder.setText(R.id.tvCommentCount, "回复 ${topic.commentCount}")
//                holder.getView<View>(R.id.root).safeClicks().subscribe {
//                    Intent(context, getActClassByArticle(article)).apply {
//                        putExtra(Param, article)
//                    }.let { context.startActivity(it) }
//                }
            }


        }
    }

    private fun getActClassByArticle(article: Article) = when (article.type) {
        1 -> CommentAct::class.java
        2 -> CommentVideoAct::class.java
        else -> CommentPdfAct::class.java
    }

}