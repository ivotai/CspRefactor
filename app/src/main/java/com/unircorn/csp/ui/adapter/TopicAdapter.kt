package com.unircorn.csp.ui.adapter

import android.content.Intent
import android.view.View
import android.widget.ImageView
import cn.jzvd.JZDataSource
import cn.jzvd.Jzvd
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.unircorn.csp.R
import com.unircorn.csp.app.*
import com.unircorn.csp.app.third.JZMediaIjk
import com.unircorn.csp.app.third.JzvdStdRv
import com.unircorn.csp.data.model.Topic
import com.unircorn.csp.data.model.Topic.Companion.topic_image
import com.unircorn.csp.data.model.Topic.Companion.topic_normal
import com.unircorn.csp.data.model.Topic.Companion.topic_video
import java.util.*

class TopicAdapter : BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder>(ArrayList()),
    LoadMoreModule {

    init {
        addItemType(topic_normal, R.layout.item_topic_normal)
        addItemType(topic_image, R.layout.item_topic_image)
        addItemType(topic_video, R.layout.item_topic_video)
    }

    override fun convert(holder: BaseViewHolder, item: MultiItemEntity) {
        when (item.itemType) {
            topic_normal -> {
                with(item as Topic) {
                    holder.setText(R.id.tvTitle, title)
                    holder.setText(R.id.tvIssueTime, issueTime.toDisplayDateFormat())
                    holder.setText(R.id.tvCommentCount, "评论 $commentCount")
                    holder.getView<View>(R.id.root).safeClicks().subscribe {
                        Intent(context, targetClass).apply {
                            putExtra(Param, this@with)
                        }.let { context.startActivity(it) }
                    }
                }
            }
            topic_video -> {
                with(item as Topic) {
                    val jzvdStd = holder.getView<JzvdStdRv>(R.id.jzvdStd)
                    val fullUrl = videos[0].fullUrl
                    val jzDataSource = JZDataSource(fullUrl, title)
                    jzDataSource.headerMap[Cookie] = "$SESSION=${Globals.session}"
                    jzvdStd.setUp(jzDataSource, Jzvd.SCREEN_NORMAL, JZMediaIjk::class.java)
                    Glide.with(context).load(baseUrl+videos[0].imageUrl).into(jzvdStd.posterImageView)
                    jzvdStd.setClickUi {
                        Intent(context, targetClass).apply {
                            putExtra(Param, this@with)
                        }.let { context.startActivity(it) }
                    }
                }
            }
            topic_image -> {
                with(item as Topic) {
                    holder.setText(R.id.tvTitle, title)
                    if (imageUrls.size > 0){
                        val imageView = holder.getView<ImageView>(R.id.imageView1)
                        imageView.visibility = View.VISIBLE
                        Glide.with(context).load(imageUrls[0]).into(imageView)
                    }
                    if (imageUrls.size > 1){
                        val imageView = holder.getView<ImageView>(R.id.imageView2)
                        imageView.visibility = View.VISIBLE
                        Glide.with(context).load(imageUrls[1]).into(imageView)
                    }
                    if (imageUrls.size > 2){
                        val imageView = holder.getView<ImageView>(R.id.imageView3)
                        imageView.visibility = View.VISIBLE
                        Glide.with(context).load(imageUrls[2]).into(imageView)
                    }
                }
            }
        }
    }


}