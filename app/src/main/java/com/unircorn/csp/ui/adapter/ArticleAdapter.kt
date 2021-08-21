package com.unircorn.csp.ui.adapter

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.unircorn.csp.R
import com.unircorn.csp.app.displayDateFormat
import com.unircorn.csp.app.safeClicks
import com.unircorn.csp.data.model.ArticleNormal
import com.unircorn.csp.data.model.ArticleWithImage
import com.unircorn.csp.data.model.normal
import com.unircorn.csp.data.model.withImage
import org.joda.time.DateTime
import java.util.*

class ArticleAdapter : BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder>(ArrayList()),
    LoadMoreModule {

    init {
        addItemType(normal, R.layout.item_article_normal)
        addItemType(withImage, R.layout.item_article_with_image)
    }

    override fun convert(holder: BaseViewHolder, item: MultiItemEntity) {
        when (item.itemType) {
            normal -> {
                item as ArticleNormal
                val article = item.article
                holder.setText(R.id.tvTitle, article.title)
                holder.setText(
                    R.id.tvPublishTime,
                    DateTime(article.publishTime).toString(displayDateFormat)
                )
                holder.setText(R.id.tvReadCount,"阅读 ${item.article.readCount}")
                holder.setText(R.id.tvCommentCount,"评论 ${item.article.commentCount}")
                holder.getView<View>(R.id.root).safeClicks().subscribe {
//                    Intent(mContext, getActClassByArticle(item.article)).apply {
//                        putExtra(Param, article.objectId)
//                    }.let { mContext.startActivity(it) }
                }
            }
            withImage -> {
                item as ArticleWithImage
                val article = item.article
                holder.setText(R.id.tvTitle, article.title)
                val ivImage = holder.getView<ImageView>(R.id.ivImage)
                Glide.with(context).load(article.cover).into(ivImage)
                holder.setText(
                    R.id.tvPublishTime,
                    DateTime(article.publishTime).toString(displayDateFormat)
                )
                holder.setText(R.id.tvReadCount,"阅读 ${item.article.readCount}")
                holder.setText(R.id.tvCommentCount,"评论 ${item.article.commentCount}")
                holder.getView<View>(R.id.root).safeClicks().subscribe {
//                    Intent(mContext, getActClassByArticle(item.article)).apply {
//                        putExtra(Param, article.objectId)
//                    }.let { mContext.startActivity(it) }
                }
            }

        }
    }

//    private fun getActClassByArticle(article: Article) = when (article.type) {
//        1 -> ArticleAct::class.java
//        2 -> ArticleVideoAct::class.java
//        else -> ArticlePdfAct::class.java
//    }

}