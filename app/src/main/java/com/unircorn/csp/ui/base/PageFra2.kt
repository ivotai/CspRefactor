package com.unircorn.csp.ui.base

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewbinding.ViewBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.rxjava.rxlife.lifeOnMain
import com.unircorn.csp.R
import com.unircorn.csp.app.*
import com.unircorn.csp.data.model.base.Page
import com.unircorn.csp.data.model.base.Response
import io.reactivex.rxjava3.core.Single


abstract class PageFra2<VB : ViewBinding, T> : BaseFra2<VB>() {

    abstract fun initPageAdapter()

    abstract fun loadPage(page: Int): Single<Response<Page<T>>>

    protected lateinit var pageAdapter: BaseQuickAdapter<T, BaseViewHolder>

    private val loadMoreModule get() = pageAdapter.loadMoreModule

    private val startPage = 1

    protected open val pageSize = defaultPageSize

    private val size
        get() = pageAdapter.data.size

    private val nextPage
        get() = size / pageSize + startPage

    override fun initViews() {
        if (hideTitleLayout) {
            mTitleLayout.visibility = View.GONE
        }

        initSwipeRefreshLayout()
        initRecyclerView()
        initLoadMoreModule()
    }

    private fun initSwipeRefreshLayout() {
        mSwipeRefreshLayout.run {
            setColorSchemeResources(R.color.white)
            setProgressBackgroundColorSchemeColor(requireContext().getAttrColor(R.attr.colorPrimary))
        }
    }

    private fun initRecyclerView() {
        mRecyclerView.run {
            layoutManager = LinearLayoutManager(context)
            initPageAdapter()
            adapter = pageAdapter
        }
        initItemDecoration(mRecyclerView)
    }

    protected open fun initItemDecoration(recyclerView: RecyclerView) {
        mRecyclerView.addDefaultItemDecoration()
    }

    private fun initLoadMoreModule() {
        loadMoreModule.run {
            // ????????? false ?????????????????????????????????
            isAutoLoadMore = true
            isEnableLoadMoreIfNotFullPage = true
        }
    }

    override fun initBindings() {
        mSwipeRefreshLayout.setOnRefreshListener { loadStartPage() }
        loadMoreModule.setOnLoadMoreListener { loadNextPage() }
        loadStartPage()
    }

    protected open fun loadStartPage() {
        mSwipeRefreshLayout.isRefreshing = true
        loadPage(startPage)
            .lifeOnMain(this)
            .subscribe(
                {
                    mSwipeRefreshLayout.isRefreshing = false
                    if (it.failed) return@subscribe
                    pageAdapter.setList(it.data.content)
                    checkIsLoadAll(it)
                },
                {
                    mSwipeRefreshLayout.isRefreshing = false
                    it.errorMsg().toast()
                }
            )
    }

    private fun loadNextPage() {
        loadPage(nextPage)
            .lifeOnMain(this)
            .subscribe(
                {
                    if (it.failed) {
                        loadMoreModule.loadMoreFail()
                        return@subscribe
                    }
                    pageAdapter.addData(it.data.content)
                    loadMoreModule.loadMoreComplete()
                    checkIsLoadAll(it)
                },
                {
                    loadMoreModule.loadMoreFail()
                    it.errorMsg().toast()
                }
            )
    }

    private fun checkIsLoadAll(pageResponse: Response<Page<T>>) {
        val isLoadAll = size == pageResponse.data.totalElements.toInt()
        if (isLoadAll) loadMoreModule.loadMoreEnd()
    }

    abstract val mRecyclerView: RecyclerView

    abstract val mSwipeRefreshLayout: SwipeRefreshLayout

    //

    abstract val mTitleLayout: ConstraintLayout

    private val hideTitleLayout by lazy { arguments?.getBoolean(HideTitleLayout, false) ?: false }

}