package com.unircorn.csp.ui.header

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.unircorn.csp.R
import com.unircorn.csp.app.MyComponent

@SuppressLint("ViewConstructor")
class TopicImageHeaderView(private val imageUrl: String) : FrameLayout(MyComponent().context) {

    init {
        initViews(context)
    }

    private fun initViews(context: Context) {
        val root = LayoutInflater.from(context).inflate(R.layout.item_image2, this, true)
        val imageView = root.findViewById<ImageView>(R.id.imageView)
        Glide.with(context).load(imageUrl).into(imageView)
    }

}