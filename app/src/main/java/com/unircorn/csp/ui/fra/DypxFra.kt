package com.unircorn.csp.ui.fra

import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ConvertUtils
import com.luck.picture.lib.decoration.GridSpacingItemDecoration
import com.rxjava.rxlife.lifeOnMain
import com.unircorn.csp.app.errorMsg
import com.unircorn.csp.app.toast
import com.unircorn.csp.databinding.FraDypxBinding
import com.unircorn.csp.ui.adapter.DypxAdapter
import com.unircorn.csp.ui.base.BaseFra2

class DypxFra : BaseFra2<FraDypxBinding>() {

    private val dypxAdapter = DypxAdapter()

    override fun initViews() = with(binding) {
        recyclerView.run {
            layoutManager = LinearLayoutManager(context)
            adapter = dypxAdapter
            addItemDecoration(GridSpacingItemDecoration(1, ConvertUtils.dp2px(16f), true))
        }
    }

    override fun initBindings() {
        getCode()
    }

    private fun getCode() {
        api.getCode(tag = "dypx").lifeOnMain(this).subscribe(
            {
                if (it.failed) return@subscribe
                dypxAdapter.setNewInstance(it.data.toMutableList())
            },
            { it.errorMsg().toast() }
        )
    }

}