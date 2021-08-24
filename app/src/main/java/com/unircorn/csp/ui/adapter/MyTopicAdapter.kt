package com.unircorn.csp.ui.adapter

import android.content.Intent
import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.jakewharton.rxbinding4.view.longClicks
import com.unircorn.csp.R
import com.unircorn.csp.app.*
import com.unircorn.csp.data.event.DeleteTopicEvent
import com.unircorn.csp.data.model.Topic

class MyTopicAdapter : BaseQuickAdapter<Topic, BaseViewHolder>(R.layout.item_topic_normal),
    LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: Topic) {
        holder.apply {
            with(item) {
                holder.setText(R.id.tvTitle, title)
                holder.setText(R.id.tvIssueTime, issueTime.toDisplayDateFormat())
                holder.setText(R.id.tvCommentCount, "评论 $commentCount")
                holder.getView<View>(R.id.root).safeClicks().subscribe {
                    Intent(context, targetClass).apply {
                        putExtra(Param, this@with)
                    }.let { context.startActivity(it) }
                }
                holder.getView<View>(R.id.root).longClicks().subscribe {
                    MaterialDialog(context).show {
                        title(text = "确认删除?")
                        positiveButton(text = "确认") { _ ->
                            RxBus.post(DeleteTopicEvent(topic = item))
                        }
                        negativeButton(text = "取消")
                    }
                }
            }
        }
    }

}