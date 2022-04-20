package com.unircorn.csp.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.unircorn.csp.R
import com.unircorn.csp.data.model.Code
import org.ocpsoft.prettytime.PrettyTime

class DypxAdapter : BaseQuickAdapter<Code, BaseViewHolder>(R.layout.item_dypx),
    LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: Code) {
        holder.apply {
            setText(R.id.tvName, item.name)
        }
    }

}