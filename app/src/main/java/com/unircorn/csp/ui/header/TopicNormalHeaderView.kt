package com.unircorn.csp.ui.header

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.TextView
import com.unircorn.csp.R
import com.unircorn.csp.app.MyComponent
import com.unircorn.csp.data.model.Topic
import org.ocpsoft.prettytime.PrettyTime
import java.util.*

@SuppressLint("ViewConstructor")
class TopicNormalHeaderView(private val topic: Topic) : FrameLayout(MyComponent().context) {

    private val prettyTime = PrettyTime()

    init {
        initViews(context)
    }

    private fun initViews(context: Context) {
        val root = LayoutInflater.from(context).inflate(R.layout.header_topic_normal, this, true)
        with(topic) {
            root.findViewById<TextView>(R.id.tvIssuer).text = issuer
            root.findViewById<TextView>(R.id.tvIssueTime).text = prettyTime.format(Date(issueTime))
            root.findViewById<TextView>(R.id.tvContent).text = content
        }
    }

}