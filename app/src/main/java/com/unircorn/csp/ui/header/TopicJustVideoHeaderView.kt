package com.unircorn.csp.ui.header

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.gongwen.marqueen.SimpleMF
import com.gongwen.marqueen.SimpleMarqueeView
import com.unircorn.csp.R
import com.unircorn.csp.app.MyComponent

@SuppressLint("ViewConstructor")
class TopicJustVideoHeaderView(context: Context) : FrameLayout(MyComponent().context) {

    init {
        initViews(context)
    }

    private fun initViews(context: Context) {
        val root =
            LayoutInflater.from(context).inflate(R.layout.header_topic_just_video, this, true)
        val simpleMarqueeView = root.findViewById<SimpleMarqueeView<String>>(R.id.simpleMarqueeView)
        val marqueeFactory: SimpleMF<String> = SimpleMF<String>(context)
        marqueeFactory.data = listOf("温馨提示：视频只能上传15秒以内视频！", "温馨提示：视频只能上传15秒以内视频！")
        simpleMarqueeView.setMarqueeFactory(marqueeFactory)
        simpleMarqueeView.startFlipping()
    }

}