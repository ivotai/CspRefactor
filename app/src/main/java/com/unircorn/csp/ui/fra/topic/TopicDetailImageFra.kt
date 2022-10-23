package com.unircorn.csp.ui.fra.topic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.hjq.bar.TitleBar
import com.unircorn.csp.databinding.FraTopicDetailImageBinding
import com.unircorn.csp.ui.base.TopicDetailFra
import com.unircorn.csp.ui.header.TopicImageHeaderView
import com.unircorn.csp.ui.header.TopicNormalHeaderView

class TopicDetailImageFra : TopicDetailFra() {

    override fun initPageAdapter() {
        super.initPageAdapter()
        pageAdapter.addHeaderView(TopicNormalHeaderView(topic))
        topic.imageUrls.forEach {
            pageAdapter.addHeaderView(TopicImageHeaderView(it))
        }
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

    override val floatingActionButton: FloatingActionButton
        get() = binding.floatingActionButton

// ----

    private var _binding: FraTopicDetailImageBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FraTopicDetailImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}