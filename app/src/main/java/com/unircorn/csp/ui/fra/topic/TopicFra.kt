package com.unircorn.csp.ui.fra.topic

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.listener.OnResultCallbackListener
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.library.fontawesome.FontAwesome
import com.mikepenz.iconics.utils.colorInt
import com.mikepenz.iconics.utils.sizeDp
import com.unircorn.csp.R
import com.unircorn.csp.app.*
import com.unircorn.csp.app.third.GlideEngine
import com.unircorn.csp.data.model.*
import com.unircorn.csp.data.model.base.Page
import com.unircorn.csp.data.model.base.Response
import com.unircorn.csp.databinding.FraTopicBinding
import com.unircorn.csp.ui.act.topic.CreateTopicAct
import com.unircorn.csp.ui.adapter.TopicAdapter
import com.unircorn.csp.ui.base.PageFra
import io.reactivex.rxjava3.core.Single


class TopicFra : PageFra<MultiItemEntity>(R.layout.fra_create_topic) {

    override fun initViews() {
        super.initViews()
        initFab()
    }

    private fun initFab() {
        binding.floatingActionButton.setImageDrawable(
            IconicsDrawable(requireContext(), FontAwesome.Icon.faw_plus).apply {
                colorInt = Color.WHITE
                sizeDp = 24
            }
        )
    }

    override fun initBindings() {
        super.initBindings()
        binding.floatingActionButton.safeClicks().subscribe {
            startAct(CreateTopicAct::class.java)
        }
    }

    override fun initPageAdapter() {
        pageAdapter = TopicAdapter()
    }

    override fun initItemDecoration(recyclerView: RecyclerView) {
        recyclerView.addDefaultItemDecoration()
    }

    override fun loadPage(page: Int): Single<Response<Page<MultiItemEntity>>> =
        api.getTopic(page = page)
            .map {
                val page1 = Page(
                    content = it.data.content.map { topic ->
                        if (topic.type == 0) TopicNormal(topic = topic)
                        else TopicVideo(topic = topic)
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

    private var _binding: FraTopicBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FraTopicBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}