package com.unircorn.csp.ui.adapter

import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.unircorn.csp.R
import com.unircorn.csp.app.Param
import com.unircorn.csp.app.safeClicks
import com.unircorn.csp.app.toDisplayDateFormat
import com.unircorn.csp.data.model.Article
import com.unircorn.csp.data.model.Article.Companion.article_image
import com.unircorn.csp.data.model.Article.Companion.article_normal
import java.util.*


class ArticleAdapter : BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder>(ArrayList()),
    LoadMoreModule {

    init {
        addItemType(article_normal, R.layout.item_article_normal)
        addItemType(article_image, R.layout.item_article_image)
    }

    override fun convert(holder: BaseViewHolder, item: MultiItemEntity) {
        with(item as Article) {
            holder.setText(R.id.tvTitle, title)
            if (itemType == article_image) {
                val ivImage = holder.getView<ImageView>(R.id.ivImage)
                Glide.with(context).load(cover).into(ivImage)
            }
            holder.setText(R.id.tvPublishTime, publishTime.toDisplayDateFormat())
            holder.setText(R.id.tvReadCount, "阅读 $readCount")
            holder.setText(R.id.tvCommentCount, "评论 $commentCount")
            holder.getView<View>(R.id.root).safeClicks().subscribe {
                Intent(context, targetClass).apply {
                    putExtra(Param, this@with)
                }.let { context.startActivity(it) }
            }

            // 已完成，未完成
            if (itemType == article_normal) {
                val tvCompleted = holder.getView<TextView>(R.id.tvCompleted)
                tvCompleted.visibility = if (item.completed == null) View.GONE else View.VISIBLE
                if (item.completed != null) tvCompleted.text =
                    if (item.completed == 0) "未完成" else "已完成"
            }
        }
    }

}

