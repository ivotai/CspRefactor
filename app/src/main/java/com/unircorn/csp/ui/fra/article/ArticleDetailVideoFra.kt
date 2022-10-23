package com.unircorn.csp.ui.fra.article

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import cn.jzvd.JZDataSource
import cn.jzvd.Jzvd
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.hjq.bar.TitleBar
import com.rxjava.rxlife.lifeOnMain
import com.unircorn.csp.app.*
import com.unircorn.csp.app.third.JZMediaAliyun
import com.unircorn.csp.data.model.MediaPlayStatus
import com.unircorn.csp.databinding.FraArticleDetailVideoBinding
import com.unircorn.csp.ui.base.ArticleDetailFra
import com.unircorn.csp.ui.header.WebViewHeaderView
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class ArticleDetailVideoFra : ArticleDetailFra() {

    override fun initBindings() {
        super.initBindings()
        setupJzvdStd()
        getArticle()
    }

    private fun setupJzvdStd() = with(binding) {
//        val url = "http://8.136.101.204" + "/v/饺子主动.mp4"
        val jzDataSource = JZDataSource(article.video.fullUrl)
        // todo headerMap 不生效
//        jzDataSource.headerMap[Cookie] = "$SESSION=${Globals.session}"
        binding.jzvdStd.setUp(jzDataSource, Jzvd.SCREEN_NORMAL, JZMediaAliyun::class.java)
        // 自动播放
        binding.jzvdStd.startVideoAfterPreloading()
    }

    private fun getArticle() {
        api.getArticle(objectId = article.objectId)
            .lifeOnMain(this)
            .subscribe(
                {
                    if (it.failed) return@subscribe
                    pageAdapter.addHeaderView(WebViewHeaderView(content = it.data.content))
                },
                { it.errorMsg().toast() }
            )
    }

    override val titleBar: TitleBar
        get() = binding.titleBar

    override val etContent: TextInputEditText
        get() = binding.etContent

    override val btnCreateComment: MaterialButton
        get() = binding.btnCreateComment

    override val mRecyclerView: RecyclerView
        get() = binding.recyclerView

    override val mSwipeRefreshLayout: SwipeRefreshLayout
        get() = binding.swipeRefreshLayout

    override val floatingActionButton: FloatingActionButton
        get() = binding.floatingActionButton

// ----

    private var _binding: FraArticleDetailVideoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FraArticleDetailVideoBinding.inflate(inflater, container, false)
        return binding.root
    }



}