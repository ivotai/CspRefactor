package com.unircorn.csp.ui.fra.topic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.blankj.utilcode.util.ToastUtils
import com.hjq.bar.OnTitleBarListener
import com.rxjava.rxlife.lifeOnMain
import com.unircorn.csp.app.*
import com.unircorn.csp.data.event.DeleteTopicEvent
import com.unircorn.csp.data.model.Topic
import com.unircorn.csp.data.model.base.Page
import com.unircorn.csp.data.model.base.Response
import com.unircorn.csp.databinding.FraMyTopicBinding
import com.unircorn.csp.ui.adapter.MyTopicAdapter
import com.unircorn.csp.ui.base.PageFra
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.functions.Consumer


class MyTopicFra : PageFra<Topic>() {

    override fun initViews() = with(binding) {
        super.initViews()
        titleBar.title = "我的发帖"
    }

    override fun initBindings() {
        super.initBindings()
        binding.titleBar.setOnTitleBarListener(object : OnTitleBarListener {
            override fun onLeftClick(view: View?) {
                finishAct()
            }

            override fun onTitleClick(view: View?) {

            }

            override fun onRightClick(view: View?) {

            }
        })
    }

    override fun initEvents() {
        RxBus.registerEvent(this, DeleteTopicEvent::class.java, Consumer {
            deleteTopic(it.topic)
        })
    }

    private fun deleteTopic(topic: Topic) {
        api.deleteTopic(topicId = topic.objectId)
            .lifeOnMain(this)
            .subscribe(
                {
                    if (it.failed) return@subscribe
                    ToastUtils.showShort("删帖成功")
                    loadStartPage()
                },
                { it.errorMsg().toast() }
            )
    }

    override fun initPageAdapter() {
        pageAdapter = MyTopicAdapter()
    }

    override fun loadPage(page: Int): Single<Response<Page<Topic>>> = api.getMyTopic(page = page)

    override val mRecyclerView: RecyclerView
        get() = binding.recyclerView

    override val mSwipeRefreshLayout: SwipeRefreshLayout
        get() = binding.swipeRefreshLayout

// ----

    private var _binding: FraMyTopicBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FraMyTopicBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}