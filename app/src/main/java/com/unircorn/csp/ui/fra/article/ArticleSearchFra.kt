package com.unircorn.csp.ui.fra.article

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.blankj.utilcode.util.ConvertUtils
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.jakewharton.rxbinding4.widget.textChanges
import com.rxjava.rxlife.lifeOnMain
import com.unircorn.csp.app.trimText
import com.unircorn.csp.data.model.base.Page
import com.unircorn.csp.data.model.base.Response
import com.unircorn.csp.databinding.FraArticleSearchBinding
import com.unircorn.csp.ui.adapter.ArticleAdapter
import com.unircorn.csp.ui.base.PageFra
import io.reactivex.rxjava3.core.Single
import java.util.concurrent.TimeUnit

class ArticleSearchFra : PageFra<MultiItemEntity>() {

    override fun initViews() {
        super.initViews()
        binding.etSearch.background = GradientDrawable().apply {
            setColor(Color.WHITE)
            cornerRadius = ConvertUtils.dp2px(10f).toFloat()
        }
    }

    override fun initBindings() {
        super.initBindings()
        binding.etSearch.textChanges()
            .debounce(500, TimeUnit.MILLISECONDS)
            .lifeOnMain(this)
            .subscribe { loadStartPage() }
    }

    override fun initPageAdapter() {
        pageAdapter = ArticleAdapter()
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

    override fun loadPage(page: Int): Single<Response<Page<MultiItemEntity>>> =
        api.getArticle(page = page, keyword = binding.etSearch.trimText())
            .map {
                val page1 = Page(
                    content = it.data.content.map { article ->
                        article as MultiItemEntity
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

    private var _binding: FraArticleSearchBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FraArticleSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}