package com.unircorn.csp.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.unircorn.csp.R
import com.unircorn.csp.data.model.Comment
import org.ocpsoft.prettytime.PrettyTime
import java.util.*

class CommentAdapter : BaseQuickAdapter<Comment, BaseViewHolder>(R.layout.item_comment),
    LoadMoreModule {

    private val prettyTime = PrettyTime()

    override fun convert(holder: BaseViewHolder, item: Comment) {
        holder.apply {
            setText(R.id.tvIssuer,item.issuer)
            setText(R.id.tvIssueTime,prettyTime.format(Date(item.issueTime)))
            setText(R.id.tvContent,item.content)
        }
    }

}