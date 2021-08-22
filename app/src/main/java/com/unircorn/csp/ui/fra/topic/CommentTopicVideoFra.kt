package com.unircorn.csp.ui.fra.topic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import cn.jzvd.JZDataSource
import cn.jzvd.Jzvd
import com.blankj.utilcode.util.ToastUtils
import com.rxjava.rxlife.lifeOnMain
import com.unircorn.csp.R
import com.unircorn.csp.app.*
import com.unircorn.csp.data.model.*
import com.unircorn.csp.data.model.base.Page
import com.unircorn.csp.data.model.base.Response
import com.unircorn.csp.databinding.FraCommentTopicVideoBinding
import com.unircorn.csp.ui.adapter.CommentAdapter
import com.unircorn.csp.ui.base.PageFra
import io.reactivex.rxjava3.core.Single

class CommentTopicVideoFra : PageFra<Comment>(R.layout.fra_comment_topic_video) {

    override fun initViews() {
        super.initViews()
        binding.titleBar.title = topic.title
        initVideo()
    }

    override fun initBindings() {
        super.initBindings()
        binding.btnCreateComment.safeClicks().subscribe { createCommentX() }
    }

    private fun initVideo() = with(binding) {
        val jzDataSource = JZDataSource(topic.videos[0].fullUrl, topic.title)
        jzDataSource.headerMap[Cookie] = "$SESSION=${Globals.session}"
        binding.jzvdStd.setUp(jzDataSource, Jzvd.SCREEN_NORMAL)
    }

    private fun createCommentX() {
        if (binding.etContent.isEmpty()) {
            ToastUtils.showShort("评论不能为空")
            return
        }
        createComment()
    }

    private fun createComment() {
        api.createCommentT(
            topicId = topic.objectId,
            createCommentParam = CreateCommentParam(content = binding.etContent.trimText())
        )
            .lifeOnMain(this)
            .subscribe(
                {
                    if (it.failed) return@subscribe
                    binding.etContent.setText("")
                    loadStartPage()
                },
                { it.toast() }
            )
    }

    override fun initPageAdapter() {
        pageAdapter = CommentAdapter()
        pageAdapter.headerWithEmptyEnable = true
    }

    override fun initItemDecoration(recyclerView: RecyclerView) {
        mRecyclerView.addDefaultItemDecoration()
    }

    override fun loadPage(page: Int): Single<Response<Page<Comment>>> =
        api.getCommentT(topicId = topic.objectId, page = page)

    private val topic by lazy { requireArguments().getSerializable(Param) as Topic }

    override val mRecyclerView: RecyclerView
        get() = binding.recyclerView

    override val mSwipeRefreshLayout: SwipeRefreshLayout
        get() = binding.swipeRefreshLayout

// ----

    private var _binding: FraCommentTopicVideoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FraCommentTopicVideoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}