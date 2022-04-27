package com.unircorn.csp.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.unircorn.csp.R
import com.unircorn.csp.data.model.Training

class TrainingAdapter : BaseQuickAdapter<Training, BaseViewHolder>(R.layout.item_training) {

    override fun convert(holder: BaseViewHolder, item: Training) {
        holder.apply {
            setText(R.id.tvName, item.name)
            setText(R.id.tvClassHour, "${item.classHour}学时")
            setText(R.id.tvIsCompleted, item.isCompleted)
        }
    }

}