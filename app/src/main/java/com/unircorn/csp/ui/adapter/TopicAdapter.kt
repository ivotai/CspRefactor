package com.unircorn.csp.ui.adapter

import android.content.Intent
import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.jzvd.JZDataSource
import cn.jzvd.Jzvd
import com.blankj.utilcode.util.ConvertUtils
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.unircorn.csp.R
import com.unircorn.csp.app.*
import com.unircorn.csp.app.third.JZMediaAliyun
import com.unircorn.csp.app.third.JzvdStdRv
import com.unircorn.csp.data.model.Topic
import com.unircorn.csp.data.model.Topic.Companion.topic_image
import com.unircorn.csp.data.model.Topic.Companion.topic_normal
import com.unircorn.csp.data.model.Topic.Companion.topic_video
import java.util.*

class TopicAdapter : BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder>(ArrayList()),
    LoadMoreModule {

    var xueXiXiaoXinDe = false

    init {
        addItemType(topic_normal, R.layout.item_topic_normal)
        addItemType(topic_image, R.layout.item_topic_image)
        addItemType(topic_video, R.layout.item_topic_video)
    }

    override fun onItemViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int) {
        if (viewType == topic_image) {
            val recyclerView = viewHolder.getView<RecyclerView>(R.id.recyclerView)
            recyclerView.run {
                layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                MaterialDividerItemDecoration(
                    context,
                    LinearLayoutManager.HORIZONTAL
                ).apply {
                    dividerColor = Color.WHITE
                    dividerThickness = ConvertUtils.dp2px(8f)
                }.let { addItemDecoration(it) }
                adapter = Image1Adapter()
            }
        }
    }

    override fun convert(holder: BaseViewHolder, item: MultiItemEntity) {
        when (item.itemType) {
            topic_normal -> {
                with(item as Topic) {
                    holder.setText(R.id.tvTitle, title)
                    holder.setText(R.id.tvIssueTime, issueTime.toDisplayDateFormat())
                    holder.setText(
                        R.id.tvCommentCount,
                        "${if (xueXiXiaoXinDe) "????????????" else "??????"} $commentCount"
                    )
                    holder.getView<View>(R.id.root).safeClicks().subscribe {
                        Intent(context, targetClass).apply {
                            putExtra(Param, this@with)
                            putExtra(XueXiXiaoXinDe,xueXiXiaoXinDe)
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
                    jzvdStd.setUp(jzDataSource, Jzvd.SCREEN_NORMAL, JZMediaAliyun::class.java)
                    Glide.with(context).load(baseUrl + videos[0].imageUrl).centerCrop()
                        .into(jzvdStd.posterImageView)
                    jzvdStd.setClickUi {
                        Intent(context, targetClass).apply {
                            putExtra(Param, this@with)
                            putExtra(XueXiXiaoXinDe,xueXiXiaoXinDe)
                        }.let { context.startActivity(it) }
                    }
                }
            }
            topic_image -> {
                with(item as Topic) {
                    holder.setText(R.id.tvTitle, title)
                    val recyclerView = holder.getView<RecyclerView>(R.id.recyclerView)
                    val imageAdapter = recyclerView.adapter as Image1Adapter
                    imageAdapter.setList(item.imageUrls)
                    holder.getView<View>(R.id.root).safeClicks().subscribe {
                        Intent(context, targetClass).apply {
                            putExtra(Param, this@with)
                            putExtra(XueXiXiaoXinDe,xueXiXiaoXinDe)
                        }.let { context.startActivity(it) }
                    }
                    holder.getView<View>(R.id.recyclerView).safeClicks().subscribe {
                        Intent(context, targetClass).apply {
                            putExtra(Param, this@with)
                            putExtra(XueXiXiaoXinDe,xueXiXiaoXinDe)
                        }.let { context.startActivity(it) }
                    }
                }
            }
        }
    }

}