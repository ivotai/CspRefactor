package com.unircorn.csp.ui.adapter

import android.content.Intent
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.unircorn.csp.R
import com.unircorn.csp.app.Param
import com.unircorn.csp.app.displayDateFormat
import com.unircorn.csp.app.safeClicks
import com.unircorn.csp.data.model.Article
import com.unircorn.csp.data.model.Article.Companion.article_image
import com.unircorn.csp.data.model.Article.Companion.article_normal
import org.joda.time.DateTime
import java.util.*


class ArticleAdapter : BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder>(ArrayList()),
    LoadMoreModule {

    init {
        addItemType(article_normal, R.layout.item_article_normal)
        addItemType(article_image, R.layout.item_article_image)
    }

    override fun convert(holder: BaseViewHolder, item: MultiItemEntity) {
        when (item.itemType) {
            article_normal -> {

                val article = item as Article
                holder.setText(R.id.tvTitle, article.title)
                holder.setText(
                    R.id.tvPublishTime,
                    DateTime(article.publishTime).toString(displayDateFormat)
                )
                holder.setText(R.id.tvReadCount, "阅读 ${article.readCount}")
                holder.setText(R.id.tvCommentCount, "评论 ${article.commentCount}")
                holder.getView<View>(R.id.root).safeClicks().subscribe {
                    Intent(context, article.targetClass).apply {
                        putExtra(Param, article)
                    }.let { context.startActivity(it) }
                }
            }
            article_image -> {
                val article = item as Article
                holder.setText(R.id.tvTitle, article.title)
                val ivImage = holder.getView<ImageView>(R.id.ivImage)
                Glide.with(context).load(article.cover).into(ivImage)
                holder.setText(
                    R.id.tvPublishTime,
                    DateTime(article.publishTime).toString(displayDateFormat)
                )
                holder.setText(R.id.tvReadCount, "阅读 ${article.readCount}")
                holder.setText(R.id.tvCommentCount, "评论 ${article.commentCount}")
                holder.getView<View>(R.id.root).safeClicks().subscribe {
                    Intent(context, article.targetClass).apply {
                        putExtra(Param, article)
                    }.let { context.startActivity(it) }
                }
            }

        }
    }


}

