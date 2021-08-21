package com.unircorn.csp.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.unircorn.csp.R
import com.unircorn.csp.data.model.Comment
import org.ocpsoft.prettytime.PrettyTime

class CommentAdapter : BaseQuickAdapter<Comment, BaseViewHolder>(R.layout.item_comment),
    LoadMoreModule {

    private val prettyTime = PrettyTime()

    override fun convert(holder: BaseViewHolder, item: Comment) {
        holder.apply {
//            tvIssuer.text = item.issuer
//            tvIssueTime.text =prettyTime.format(Date(item.issueTime))
//            tvContent.text = item.content
        }
    }

}