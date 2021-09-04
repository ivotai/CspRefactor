package com.unircorn.csp.ui.fra.article

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.unircorn.csp.app.Category
import com.unircorn.csp.app.Title
import com.unircorn.csp.data.model.base.Page
import com.unircorn.csp.data.model.base.Response
import com.unircorn.csp.databinding.UiPageBinding
import com.unircorn.csp.ui.adapter.ArticleAdapter
import com.unircorn.csp.ui.base.PageFra2
import io.reactivex.rxjava3.core.Single

open class ArticleFra : PageFra2<UiPageBinding, MultiItemEntity>() {

    override fun initViews() = with(binding) {
        super.initViews()
        tvTitle.text = title
    }

    override fun initBindings() {
        super.initBindings()
        // todo search and my
//        Intent(requireContext(), ArticleSearchAct::class.java)
//            .apply { putExtra(Param, category) }
//            .let { requireContext().startActivity(it) }

//        startAct(MyAct::class.java)
    }

    override fun initPageAdapter() {
        pageAdapter = ArticleAdapter()
    }

    override fun loadPage(page: Int): Single<Response<Page<MultiItemEntity>>> =
        api.getArticle(page = page, category = category)
            .map {
                val pageShadow = Page(
                    content = it.data.content.map { article ->
                        article as MultiItemEntity
                    },
                    totalElements = it.data.totalElements
                )
                return@map Response(
                    message = it.message,
                    success = it.success,
                    data = pageShadow
                )
            }


    private val title by lazy { requireArguments().getString(Title, "") }
    private val category by lazy { requireArguments().getString(Category, "") }

    override val mRecyclerView: RecyclerView
        get() = binding.recyclerView

    override val mSwipeRefreshLayout: SwipeRefreshLayout
        get() = binding.swipeRefreshLayout

    override val mTitleLayout: ConstraintLayout
        get() = binding.titleLayout

}