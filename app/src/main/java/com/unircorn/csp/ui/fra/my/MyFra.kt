package com.unircorn.csp.ui.fra.my

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.hjq.bar.OnTitleBarListener
import com.rxjava.rxlife.lifeOnMain
import com.unircorn.csp.app.Globals
import com.unircorn.csp.app.toast
import com.unircorn.csp.data.model.MyMenu
import com.unircorn.csp.databinding.FraMyBinding
import com.unircorn.csp.ui.adapter.MyAdapter
import com.unircorn.csp.ui.base.BaseFra

class MyFra : BaseFra() {

    override fun initViews() = with(binding) {
        tvUsername.text = Globals.user.username
        chipCourtName.text = Globals.user.courtName

        fun initRecyclerView() {
            recyclerView.apply {
                overScrollMode = RecyclerView.OVER_SCROLL_NEVER
                layoutManager = LinearLayoutManager(context)
                this.adapter = simpleAdapter
                MaterialDividerItemDecoration(
                    requireContext(),
                    LinearLayoutManager.VERTICAL
                ).apply {
                    dividerThickness = 1
                }.let { addItemDecoration(it) }
            }
        }
        initRecyclerView()
    }

    override fun initBindings() {
        with(binding) {
            simpleAdapter.setNewInstance(MyMenu.all.toMutableList())

            titleBar.setOnTitleBarListener(object : OnTitleBarListener {
                override fun onLeftClick(v: View?) {
                    requireActivity().finish()
                }

                override fun onRightClick(v: View?) {
                }

                override fun onTitleClick(v: View?) {
                }
            })

            api.getSummary()
                .lifeOnMain(this@MyFra)
                .subscribe(
                    {
                        tvReadCount.text = it.data.readCount
                        tvTopicCount.text = it.data.topicCount
                        tvReplyCount.text = it.data.replyCount
                    },
                    { it.toast() }
                )
        }
    }

    private val simpleAdapter = MyAdapter()

// ----

    private var _binding: FraMyBinding? = null

    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FraMyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}