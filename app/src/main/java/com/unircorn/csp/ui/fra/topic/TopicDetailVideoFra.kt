package com.unircorn.csp.ui.fra.topic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import cn.jzvd.JZDataSource
import cn.jzvd.Jzvd
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.hjq.bar.TitleBar
import com.unircorn.csp.app.*
import com.unircorn.csp.app.third.JZMediaIjk
import com.unircorn.csp.databinding.FraTopicDetailVideoBinding
import com.unircorn.csp.ui.base.TopicDetailFra

class TopicDetailVideoFra : TopicDetailFra() {

    override fun initViews() {
        super.initViews()
        binding.titleBar.title = topic.title
        setUpJzvdStd()
    }

    private fun setUpJzvdStd() = with(binding) {
        val jzDataSource = JZDataSource(topic.videos[0].fullUrl)
        jzDataSource.headerMap[Cookie] = "$SESSION=${Globals.session}"
        binding.jzvdStd.setUp(jzDataSource, Jzvd.SCREEN_NORMAL, JZMediaIjk::class.java)
    }

    override val titleBar: TitleBar
        get() = binding.titleBar

    override val etContent: TextInputEditText
        get() = binding.etContent

    override val btnCreateComment: MaterialButton
        get() = binding.btnCreateComment

    override val mRecyclerView: RecyclerView
        get() = binding.recyclerView

    override val mSwipeRefreshLayout: SwipeRefreshLayout
        get() = binding.swipeRefreshLayout

// ----

    private var _binding: FraTopicDetailVideoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FraTopicDetailVideoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}