package com.unircorn.csp.ui.fra

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.blankj.utilcode.util.ToastUtils
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.hjq.bar.OnTitleBarListener
import com.rxjava.rxlife.lifeOnMain
import com.unircorn.csp.R
import com.unircorn.csp.app.*
import com.unircorn.csp.data.model.Article
import com.unircorn.csp.data.model.Comment
import com.unircorn.csp.data.model.CreateCommentParam
import com.unircorn.csp.data.model.base.Page
import com.unircorn.csp.data.model.base.Response
import com.unircorn.csp.databinding.FraCommentBinding
import com.unircorn.csp.ui.adapter.CommentAdapter
import com.unircorn.csp.ui.base.PageFra
import io.reactivex.rxjava3.core.Single

class CommentFra : PageFra<Comment>(R.layout.fra_comment) {

    override fun initViews() {
        super.initViews()
        binding.titleBar.title = article.title
    }

    override fun initBindings() {
        super.initBindings()
        binding.titleBar.setOnTitleBarListener(object : OnTitleBarListener {
            override fun onLeftClick(v: View?) {
               requireActivity().finish()
            }

            override fun onRightClick(v: View?) {

            }

            override fun onTitleClick(v: View?) {
            }
        })
        binding.btnCreateComment.safeClicks().subscribe { createCommentX() }
    }

    private fun createCommentX() {
        if (binding.etContent.isEmpty()) {
            ToastUtils.showShort("评论不能为空")
            return
        }
        createComment()
    }

    private fun createComment() {
        api.createComment(
            articleId = article.objectId,
            createCommentParam = CreateCommentParam(content = binding.etContent.trimText())
        )
            .lifeOnMain(this)
            .subscribe(
                {
                    if (it.failed) return@subscribe
                    binding.etContent.setText("")
                    loadStartPage()
                },
                {it.toast()}
            )
    }



    override fun initPageAdapter() {
        pageAdapter = CommentAdapter()
        // 根据 type 添加三种头 todo
//        pageAdapter.addHeaderView(TopicHeader(context = this,topic = topic))
        // 当空数据时显示空布局和HeaderView
        pageAdapter.headerWithEmptyEnable =true
    }

    override fun initItemDecoration(recyclerView: RecyclerView) {
        recyclerView.apply {
            MaterialDividerItemDecoration(
                requireContext(),
                LinearLayoutManager.VERTICAL
            ).apply {
                dividerThickness = 1
            }.let { addItemDecoration(it) }
        }
    }

    override fun loadPage(page: Int): Single<Response<Page<Comment>>> =
        api.getComment(articleId = article.objectId, page = page)

    private val article by lazy { requireArguments().getSerializable(Param) as Article }

    override val mRecyclerView: RecyclerView
        get() = binding.recyclerView

    override val mSwipeRefreshLayout: SwipeRefreshLayout
        get() = binding.swipeRefreshLayout

// ----

    private var _binding: FraCommentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FraCommentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}