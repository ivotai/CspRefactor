package com.unircorn.csp.ui.adapter

import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.unircorn.csp.R

class Image2Adapter : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_image2) {

    override fun convert(holder: BaseViewHolder, item: String) {
        holder.apply {
            val image = holder.getView<ImageView>(R.id.imageView)
            val lp = image.layoutParams
            lp.height = ConstraintLayout.LayoutParams.WRAP_CONTENT
            Glide.with(context).load(item).into(image)
        }
    }

}