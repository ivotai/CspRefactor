package com.unircorn.csp.ui.header

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.unircorn.csp.R
import com.unircorn.csp.app.MyComponent
import com.unircorn.csp.data.model.Topic
import com.unircorn.csp.ui.adapter.Image2Adapter

@SuppressLint("ViewConstructor")
class TopicImageHeaderView(private val topic: Topic) : FrameLayout(MyComponent().context) {

    init {
        initViews(context)
    }

    private fun initViews(context: Context) {
        val root = LayoutInflater.from(context).inflate(R.layout.header_topic_image, this, true)
        val recyclerView = root.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.run {
            layoutManager = LinearLayoutManager(context,RecyclerView.VERTICAL, false)
            val image2Adapter = Image2Adapter()
            adapter = image2Adapter
            image2Adapter.setList(topic.imageUrls)

            PagerSnapHelper().attachToRecyclerView(this)
        }
    }

}