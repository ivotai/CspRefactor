package com.unircorn.csp.ui.adapter

import android.content.Intent
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.unircorn.csp.R
import com.unircorn.csp.app.Category
import com.unircorn.csp.app.Title
import com.unircorn.csp.app.safeClicks
import com.unircorn.csp.data.model.Code
import com.unircorn.csp.ui.act.article.ArticlePageAct
import com.unircorn.csp.ui.act.article.ArticlePageActForDypx

class DypxAdapter : BaseQuickAdapter<Code, BaseViewHolder>(R.layout.item_dypx) {

    override fun convert(holder: BaseViewHolder, item: Code) {
        holder.apply {
            setText(R.id.tvName, item.name)

            getView<View>(R.id.root).safeClicks().subscribe {
                context.startActivity(Intent(context, ArticlePageActForDypx::class.java).apply {
                    putExtra(Title, item.name)
                    putExtra(Category, item.objectId)
                })
            }
        }
    }

}