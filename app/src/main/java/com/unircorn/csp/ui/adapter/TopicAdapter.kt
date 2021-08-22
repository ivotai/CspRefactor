package com.unircorn.csp.ui.adapter

import android.content.Intent
import android.view.View
import cn.jzvd.JZDataSource
import cn.jzvd.Jzvd
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.unircorn.csp.R
import com.unircorn.csp.app.*
import com.unircorn.csp.app.third.JzvdStdRv
import com.unircorn.csp.data.model.TopicNormal
import com.unircorn.csp.data.model.TopicVideo
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
        addItemType(topic_video, R.layout.item_topic_video)
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
                holder.getView<View>(R.id.root).safeClicks().subscribe {
                    Intent(context, topic.targetClass).apply {
                        putExtra(Param, topic)
                    }.let { context.startActivity(it) }
                }
            }
            topic_video -> {
                item as TopicVideo
                val topic = item.topic
                val jzvdStd = holder.getView<JzvdStdRv>(R.id.jzvdStd)
                val jzDataSource = JZDataSource(topic.videos[0].fullUrl, topic.title)
                jzDataSource.headerMap[Cookie] = "$SESSION=${Globals.session}"
                jzvdStd.setUp(jzDataSource, Jzvd.SCREEN_NORMAL)
                Glide.with(context).load(topic.videos[0].imageUrl)
                    .into(jzvdStd.posterImageView)
                jzvdStd.setClickUi {
                    Intent(context, topic.targetClass).apply {
                        putExtra(Param, topic)
                    }.let { context.startActivity(it) }
                }
            }
        }
    }


}