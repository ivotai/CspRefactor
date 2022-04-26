package com.unircorn.csp.ui.fra.my

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.hjq.bar.OnTitleBarListener
import com.rxjava.rxlife.lifeOnMain
import com.unircorn.csp.app.*
import com.unircorn.csp.data.event.ModifyDepartmentEvent
import com.unircorn.csp.data.model.MyMenu
import com.unircorn.csp.databinding.FraMyBinding
import com.unircorn.csp.ui.act.topic.MyTopicAct
import com.unircorn.csp.ui.adapter.MyAdapter
import com.unircorn.csp.ui.base.BaseFra2

class MyFra : BaseFra2<FraMyBinding>() {

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
                    { it.errorMsg().toast() }
                )

            tvTopicCount.safeClicks().subscribe { startAct(MyTopicAct::class.java) }

            // todo 跳转到培训情况
            tvTraining.safeClicks().subscribe { }
        }
    }

    override fun initEvents() {
        RxBus.registerEvent(this, ModifyDepartmentEvent::class.java, {
            Globals.loginResponse.user.department = it.department
            simpleAdapter.notifyDataSetChanged()
        })
    }

    private val simpleAdapter = MyAdapter()

}