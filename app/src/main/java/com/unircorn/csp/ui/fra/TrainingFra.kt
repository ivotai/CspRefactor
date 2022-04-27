package com.unircorn.csp.ui.fra

import androidx.recyclerview.widget.LinearLayoutManager
import com.rxjava.rxlife.lifeOnMain
import com.unircorn.csp.app.errorMsg
import com.unircorn.csp.app.toast
import com.unircorn.csp.databinding.FraTrainingBinding
import com.unircorn.csp.ui.adapter.TrainingAdapter
import com.unircorn.csp.ui.base.BaseFra2

class TrainingFra : BaseFra2<FraTrainingBinding>() {

    private val simpleAdapter = TrainingAdapter()

    override fun initViews() = with(binding) {
        recyclerView.run {
            layoutManager = LinearLayoutManager(context)
            adapter = simpleAdapter
//            addItemDecoration(GridSpacingItemDecoration(1, ConvertUtils.dp2px(16f), true))
        }
    }

    override fun initBindings() {
        getTraining()
    }

    private fun getTraining() {
        api.getTraining().lifeOnMain(this).subscribe(
            {
                if (it.failed) return@subscribe
                simpleAdapter.setNewInstance(it.data.toMutableList())
            },
            { it.errorMsg().toast() }
        )
    }

}