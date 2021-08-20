package com.unircorn.csp.ui

import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.rxjava.rxlife.lifeOnMain
import com.unircorn.csp.R
import com.unircorn.csp.data.model.Page
import com.unircorn.csp.data.model.Response
import io.reactivex.rxjava3.core.Single


abstract class PageFra<T>(@LayoutRes contentLayoutId: Int = R.layout.ui_swipe_recycler) :
    BaseFra(contentLayoutId = contentLayoutId) {

    abstract fun initPageAdapter()

    abstract fun loadPage(page: Int): Single<Response<Page<T>>>

    protected lateinit var pageAdapter: BaseQuickAdapter<T, BaseViewHolder>

    private val loadMoreModule get() = pageAdapter.loadMoreModule

    private val startPage = 1

    protected open val pageSize = 5

    private val size
        get() = pageAdapter.data.size

    private val nextPage
        get() = size / pageSize + startPage

    override fun initViews() {
        initSwipeRefreshLayout()
        initRecyclerView()
        initLoadMoreModule()
    }

    private fun initSwipeRefreshLayout() {
//        mSwipeRefreshLayout.
//        mSwipeRefreshLayout.run {
//            setColorSchemeResources(R.color.white)
//            setProgressBackgroundColorSchemeResource(R.color.blue_500)
//        }
    }

    private fun initRecyclerView() {
        mRecyclerView.run {
            layoutManager = LinearLayoutManager(context)
            initPageAdapter()
            adapter = pageAdapter
        }
    }

    private fun initLoadMoreModule() {
        loadMoreModule.run {
            // 设置为 false 可以更好地测试分页逻辑
            isAutoLoadMore = true
            isEnableLoadMoreIfNotFullPage = true
        }
    }

    override fun initBindings() {
        mSwipeRefreshLayout.setOnRefreshListener { loadStartPage() }
        loadMoreModule.setOnLoadMoreListener { loadNextPage() }
        loadStartPage()
    }

    private fun loadStartPage() {
        mSwipeRefreshLayout.isRefreshing = true
        loadPage(startPage)
            .lifeOnMain(this)
            .subscribe({
                mSwipeRefreshLayout.isRefreshing = false
                if (it.failed) return@subscribe
                pageAdapter.setList(it.data.content)
                checkIsLoadAll(it)
                setEmptyViewIfNeed(it)
            }, {
                mSwipeRefreshLayout.isRefreshing = false
//                it.showPrompt()
            })
    }

    private fun setEmptyViewIfNeed(pageResponse: Response<Page<T>>) {
//        if (pageResponse.data.content.isEmpty()) pageAdapter.setEmptyView(R.layout.ui_empty_view)
    }

    private fun loadNextPage() {
        loadPage(nextPage)
            .lifeOnMain(this)
            .subscribe({
                if (it.failed) {
                    loadMoreModule.loadMoreFail()
                    return@subscribe
                }
                pageAdapter.addData(it.data.content)
                loadMoreModule.loadMoreComplete()
                checkIsLoadAll(it)
            }, {
                loadMoreModule.loadMoreFail()
//                it.showPrompt()
            })
    }

    private fun checkIsLoadAll(pageResponse: Response<Page<T>>) {
        val isLoadAll = size == pageResponse.data.totalElements.toInt()
        if (isLoadAll) loadMoreModule.loadMoreEnd()
    }

    abstract val mRecyclerView: RecyclerView

    abstract val mSwipeRefreshLayout: SwipeRefreshLayout

}