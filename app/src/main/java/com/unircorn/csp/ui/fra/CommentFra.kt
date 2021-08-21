package com.unircorn.csp.ui.fra

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.unircorn.csp.R
import com.unircorn.csp.app.Param
import com.unircorn.csp.data.model.Article
import com.unircorn.csp.data.model.Comment
import com.unircorn.csp.data.model.base.Page
import com.unircorn.csp.data.model.base.Response
import com.unircorn.csp.databinding.UiTitleSwipeBinding
import com.unircorn.csp.ui.adapter.CommentAdapter
import com.unircorn.csp.ui.base.PageFra
import io.reactivex.rxjava3.core.Single

class CommentFra : PageFra<Comment>(R.layout.ui_title_swipe) {

    override fun initViews() {
        super.initViews()
        binding.titleBar.title = article.title
    }

    override fun initPageAdapter() {
        pageAdapter = CommentAdapter()
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

    private var _binding: UiTitleSwipeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = UiTitleSwipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}