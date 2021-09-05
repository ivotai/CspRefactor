package com.unircorn.csp.ui.fra.topic

import android.content.Intent
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnChildAttachStateChangeListener
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import cn.jzvd.Jzvd
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.utils.sizeDp
import com.unicorn.sanre.icon.Fas
import com.unircorn.csp.R
import com.unircorn.csp.app.*
import com.unircorn.csp.data.event.RefreshTopicEvent
import com.unircorn.csp.data.model.*
import com.unircorn.csp.data.model.base.Page
import com.unircorn.csp.data.model.base.Response
import com.unircorn.csp.databinding.TopicPageFraBinding
import com.unircorn.csp.ui.act.topic.CreateTopicAct
import com.unircorn.csp.ui.adapter.TopicAdapter
import com.unircorn.csp.ui.base.PageFra2
import com.unircorn.csp.ui.header.TopicJustVideoHeaderView
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.functions.Consumer

open class TopicPageFra : PageFra2<TopicPageFraBinding, MultiItemEntity>() {

    protected open val justVideo = false

    override fun initViews(): Unit = with(binding) {
        super.initViews()
//        tvTitle.text = title

        initFloatingActionButton()

        if (justVideo) {
            pageAdapter.addHeaderView(TopicJustVideoHeaderView(requireContext()))
        }
    }

//    private val title by lazy { requireArguments().getString(Title, "") }

    private fun initFloatingActionButton() {
        binding.floatingActionButton.setImageDrawable(
            IconicsDrawable(requireContext(), Fas.Icon.fas_plus).apply {
                sizeDp = 24
            }
        )
    }

    override fun initBindings() {
        super.initBindings()
        addOnChildAttachStateChangeListener()
        binding.floatingActionButton.safeClicks().subscribe {
            Intent(requireContext(), CreateTopicAct::class.java).apply {
                putExtra(Param, justVideo)
            }.let { startActivity(it) }
        }
    }

    private fun addOnChildAttachStateChangeListener() {
        mRecyclerView.addOnChildAttachStateChangeListener(object :
            OnChildAttachStateChangeListener {
            override fun onChildViewAttachedToWindow(view: View) {}
            override fun onChildViewDetachedFromWindow(view: View) {
                val jzvd: Jzvd? = view.findViewById(R.id.jzvdStd)
                if (jzvd != null && Jzvd.CURRENT_JZVD != null &&
                    jzvd.jzDataSource.containsTheUrl(Jzvd.CURRENT_JZVD.jzDataSource.currentUrl)
                ) {
                    if (Jzvd.CURRENT_JZVD != null && Jzvd.CURRENT_JZVD.screen != Jzvd.SCREEN_FULLSCREEN) {
                        Jzvd.releaseAllVideos()
                    }
                }
            }
        })
    }

    override fun initEvents() {
        RxBus.registerEvent(this, RefreshTopicEvent::class.java, Consumer {
            loadStartPage()
        })
    }

    override fun initPageAdapter() {
        pageAdapter = TopicAdapter()
    }

    override fun loadPage(page: Int): Single<Response<Page<MultiItemEntity>>> =
        api.getTopic(page = page, type = if (justVideo) 2 else 1)
            .map {
                val page1 = Page(
                    content = it.data.content.map { topic ->
                        topic as MultiItemEntity
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

    override val mTitleLayout: ConstraintLayout
        get() = binding.titleLayout

}