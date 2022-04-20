package com.unircorn.csp.ui.fra

import com.unircorn.csp.databinding.FraStudySummaryBinding
import com.unircorn.csp.ui.base.BaseFra2

class StudySummaryFra : BaseFra2<FraStudySummaryBinding>() {

    override fun initViews() = with(binding) {
        tvTitle.text = "学习统计"
//        barChart
    }

}