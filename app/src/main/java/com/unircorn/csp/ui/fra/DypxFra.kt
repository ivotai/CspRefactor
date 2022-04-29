package com.unircorn.csp.ui.fra

import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ConvertUtils
import com.luck.picture.lib.decoration.GridSpacingItemDecoration
import com.rxjava.rxlife.lifeOnMain
import com.unircorn.csp.R
import com.unircorn.csp.app.errorMsg
import com.unircorn.csp.app.getAttrColor
import com.unircorn.csp.app.toast
import com.unircorn.csp.databinding.FraDypxBinding
import com.unircorn.csp.ui.adapter.DypxAdapter
import com.unircorn.csp.ui.base.BaseFra2

class DypxFra : BaseFra2<FraDypxBinding>() {

    private val simpleAdapter = DypxAdapter()

    override fun initViews() = with(binding) {
        recyclerView.run {
            layoutManager = LinearLayoutManager(context)
            adapter = simpleAdapter
            addItemDecoration(GridSpacingItemDecoration(1, ConvertUtils.dp2px(16f), true))
        }
        swipeRefreshLayout.run {
            setColorSchemeResources(R.color.white)
            setProgressBackgroundColorSchemeColor(requireContext().getAttrColor(R.attr.colorPrimary))
        }
    }

    override fun initBindings() {
        binding.swipeRefreshLayout.setOnRefreshListener { getCode() }
        getCode()

    }

    private fun getCode() {
        binding.swipeRefreshLayout.isRefreshing = true
        api.getTraining().lifeOnMain(this).subscribe(
            {
                binding.swipeRefreshLayout.isRefreshing = false
                if (it.failed) return@subscribe
                simpleAdapter.setNewInstance(it.data.toMutableList())
            },
            {
                binding.swipeRefreshLayout.isRefreshing = false
                it.errorMsg().toast()
            }
        )
    }

}