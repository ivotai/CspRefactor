package com.unircorn.csp.ui.fra.topic

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItems
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.hjq.bar.OnTitleBarListener
import com.kaopiz.kprogresshud.KProgressHUD
import com.rxjava.rxlife.lifeOnMain
import com.unircorn.csp.R
import com.unircorn.csp.app.*
import com.unircorn.csp.app.helper.FileUtils2
import com.unircorn.csp.data.model.*
import com.unircorn.csp.data.model.base.Page
import com.unircorn.csp.data.model.base.Response
import com.unircorn.csp.databinding.FraCommentNormalBinding
import com.unircorn.csp.databinding.UiSwipeBinding
import com.unircorn.csp.ui.adapter.CommentAdapter
import com.unircorn.csp.ui.adapter.TopicAdapter
import com.unircorn.csp.ui.base.PageFra
import com.unircorn.csp.ui.header.WebViewHeaderView
import io.reactivex.rxjava3.core.Single
import rxhttp.RxHttp
import rxhttp.wrapper.exception.HttpStatusCodeException
import java.io.File

class TopicFra : PageFra<MultiItemEntity>(R.layout.ui_swipe) {

    override fun initPageAdapter() {
        pageAdapter = TopicAdapter()
    }

    override fun initItemDecoration(recyclerView: RecyclerView) {
        recyclerView.addDefaultItemDecoration()
    }

    override fun loadPage(page: Int): Single<Response<Page<MultiItemEntity>>> =
        api.getTopic(page = page)
            .map {
                val page1 = Page(
                    content = it.data.content.map { topic ->
                        if (topic.type ==0)TopicNormal(topic = topic)
                        else TopicVideo(topic = topic)
                    },
                    totalElements = it.data.totalElements
                )
                return@map Response(
                    message = it.message,
                    success = it.success,
                    data = page1
                )
            }

    override val mRecyclerView: RecyclerView
        get() = binding.recyclerView

    override val mSwipeRefreshLayout: SwipeRefreshLayout
        get() = binding.swipeRefreshLayout

// ----

    private var _binding: UiSwipeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = UiSwipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}