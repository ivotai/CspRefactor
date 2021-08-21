package com.unircorn.csp.ui.header

import android.view.LayoutInflater
import android.webkit.WebView
import android.widget.FrameLayout
import com.unircorn.csp.R
import com.unircorn.csp.app.MyComponent
import com.unircorn.csp.data.model.Article

class ArticleHeaderType1(private val article: Article) : FrameLayout(MyComponent().context) {

    init {
        initViews()
    }

    private fun initViews() {
        val root = LayoutInflater.from(context).inflate(R.layout.header_article_type1, this, true)
        val webView = root.findViewById<WebView>(R.id.webView)
        webView.loadDataWithBaseURL(null, article.content, "text/html", "utf-8", null);
    }

}