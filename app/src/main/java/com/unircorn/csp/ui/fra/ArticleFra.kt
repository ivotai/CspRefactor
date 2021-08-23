package com.unircorn.csp.ui.fra

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.hjq.bar.OnTitleBarListener
import com.unircorn.csp.app.Param
import com.unircorn.csp.app.startAct
import com.unircorn.csp.data.model.base.Page
import com.unircorn.csp.data.model.base.Response
import com.unircorn.csp.databinding.FraArticleBinding
import com.unircorn.csp.ui.act.article.ArticleSearchAct
import com.unircorn.csp.ui.act.my.MyAct
import com.unircorn.csp.ui.adapter.ArticleAdapter
import com.unircorn.csp.ui.base.PageFra
import com.unircorn.csp.ui.fraStateAdapter.MainFraStateAdapter
import io.reactivex.rxjava3.core.Single

class ArticleFra : PageFra<MultiItemEntity>() {

    override fun initViews() = with(binding) {
        super.initViews()
        titleBar.title = mTitle

    }

    override fun initBindings() {
        super.initBindings()
        binding.titleBar.setOnTitleBarListener(object : OnTitleBarListener {
            override fun onLeftClick(view: View?) {
                Intent(requireContext(), ArticleSearchAct::class.java)
                    .apply { putExtra(Param, category) }
                    .let { requireContext().startActivity(it) }
            }

            override fun onTitleClick(view: View?) {

            }

            override fun onRightClick(view: View?) {
                startAct(MyAct::class.java)
            }
        })
    }

    override fun initPageAdapter() {
        pageAdapter = ArticleAdapter()
    }

    override fun loadPage(page: Int): Single<Response<Page<MultiItemEntity>>> =
        api.getArticle(page = page, category = category)
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

    private val mTitle by lazy { MainFraStateAdapter.titles[position] }
    private val category by lazy { MainFraStateAdapter.categories[position] }
    private val position by lazy { requireArguments().getInt(Param, 0) }

    override val mRecyclerView: RecyclerView
        get() = binding.recyclerView

    override val mSwipeRefreshLayout: SwipeRefreshLayout
        get() = binding.swipeRefreshLayout

    // ----

    private var _binding: FraArticleBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FraArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}