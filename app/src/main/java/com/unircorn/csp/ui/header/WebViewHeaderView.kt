package com.unircorn.csp.ui.header

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.webkit.WebView
import android.widget.FrameLayout
import com.unircorn.csp.R
import com.unircorn.csp.app.MyComponent

@SuppressLint("ViewConstructor")
class WebViewHeaderView(private val content: String) : FrameLayout(MyComponent().context) {

    init {
        initViews()
    }

    private fun initViews() {
        val root = LayoutInflater.from(context).inflate(R.layout.header_web_view, this, true)
        val webView = root.findViewById<WebView>(R.id.webView)
        webView.loadDataWithBaseURL(null, content, "text/html", "utf-8", null);
    }

}