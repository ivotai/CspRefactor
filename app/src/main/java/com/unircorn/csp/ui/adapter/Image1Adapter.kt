package com.unircorn.csp.ui.adapter

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.unircorn.csp.R

class Image1Adapter : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_image1) {

    override fun convert(holder: BaseViewHolder, item: String) {
        holder.apply {
            val image = holder.getView<ImageView>(R.id.imageView)
            Glide.with(context).load(item).centerCrop().into(image)
        }
    }

}