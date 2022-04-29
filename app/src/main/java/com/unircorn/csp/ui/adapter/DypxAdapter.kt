package com.unircorn.csp.ui.adapter

import android.content.Intent
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.unircorn.csp.R
import com.unircorn.csp.app.Category
import com.unircorn.csp.app.Title
import com.unircorn.csp.app.Training
import com.unircorn.csp.app.safeClicks
import com.unircorn.csp.data.model.Training
import com.unircorn.csp.ui.act.article.ArticlePageActForDypx

class DypxAdapter : BaseQuickAdapter<Training, BaseViewHolder>(R.layout.item_dypx) {

    override fun convert(holder: BaseViewHolder, item: Training) {
        holder.apply {
            setText(R.id.tvName, item.name)
            setText(R.id.tvCompleted, if (item.completed == 0) "未完成" else "已完成")

            getView<View>(R.id.root).safeClicks().subscribe {
                context.startActivity(Intent(context, ArticlePageActForDypx::class.java).apply {
                    putExtra(Title, item.name)
                    putExtra(Category, item.objectId)
                    putExtra(Training, item)
                })
            }
        }
    }

}