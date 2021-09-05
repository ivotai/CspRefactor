package com.unircorn.csp.ui.fra.article

import android.content.Intent
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.unircorn.csp.app.*
import com.unircorn.csp.data.model.base.Page
import com.unircorn.csp.data.model.base.Response
import com.unircorn.csp.databinding.FraArticlePageBinding
import com.unircorn.csp.ui.act.article.ArticleSearchAct
import com.unircorn.csp.ui.act.my.MyAct
import com.unircorn.csp.ui.adapter.ArticleAdapter
import com.unircorn.csp.ui.base.PageFra2
import io.reactivex.rxjava3.core.Single

open class ArticlePageFra : PageFra2<FraArticlePageBinding, MultiItemEntity>() {

    override fun initViews() = with(binding) {
        super.initViews()
        tvTitle.text = title
    }

    override fun initBindings(): Unit = with(binding) {
        super.initBindings()
        ivSearch.safeClicks().subscribe {
            Intent(requireContext(), ArticleSearchAct::class.java)
                .apply { putExtra(Param, category) }
                .let { requireContext().startActivity(it) }
        }
        ivMy.safeClicks().subscribe { startAct(MyAct::class.java) }
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


    private val title by lazy { arguments?.getString(Title, "") }
    private val category by lazy { requireArguments().getString(Category, "") }

    override val mRecyclerView: RecyclerView
        get() = binding.recyclerView

    override val mSwipeRefreshLayout: SwipeRefreshLayout
        get() = binding.swipeRefreshLayout

    override val mTitleLayout: ConstraintLayout
        get() = binding.titleLayout

}