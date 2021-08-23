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
import org.joda.time.DateTime
import java.util.*

class ArticleNormal(val article: Article) : MultiItemEntity {
    override val itemType = ArticleAdapter.article_normal
}

class ArticleImage(val article: Article) : MultiItemEntity {
    override val itemType = ArticleAdapter.article_image
}

class ArticleAdapter : BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder>(ArrayList()),
    LoadMoreModule {

    companion object {
        const val article_normal = 0
        const val article_image = 1
    }

    init {
        addItemType(article_normal, R.layout.item_article_normal)
        addItemType(article_image, R.layout.item_article_image)
    }

    override fun convert(holder: BaseViewHolder, item: MultiItemEntity) {
        when (item.itemType) {
            article_normal -> {
                item as ArticleNormal
                val article = item.article
                holder.setText(R.id.tvTitle, article.title)
                holder.setText(
                    R.id.tvPublishTime,
                    DateTime(article.publishTime).toString(displayDateFormat)
                )
                holder.setText(R.id.tvReadCount, "阅读 ${item.article.readCount}")
                holder.setText(R.id.tvCommentCount, "评论 ${item.article.commentCount}")
                holder.getView<View>(R.id.root).safeClicks().subscribe {
                    Intent(context, article.targetClass).apply {
                        putExtra(Param, article)
                    }.let { context.startActivity(it) }
                }
            }
            article_image -> {
                item as ArticleImage
                val article = item.article
                holder.setText(R.id.tvTitle, article.title)
                val ivImage = holder.getView<ImageView>(R.id.ivImage)
                Glide.with(context).load(article.cover).into(ivImage)
                holder.setText(
                    R.id.tvPublishTime,
                    DateTime(article.publishTime).toString(displayDateFormat)
                )
                holder.setText(R.id.tvReadCount, "阅读 ${item.article.readCount}")
                holder.setText(R.id.tvCommentCount, "评论 ${item.article.commentCount}")
                holder.getView<View>(R.id.root).safeClicks().subscribe {
                    Intent(context, article.targetClass).apply {
                        putExtra(Param, article)
                    }.let { context.startActivity(it) }
                }
            }

        }
    }


}

