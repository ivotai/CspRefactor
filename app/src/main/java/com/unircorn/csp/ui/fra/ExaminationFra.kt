package com.unircorn.csp.ui.fra

import com.rxjava.rxlife.lifeOnMain
import com.unircorn.csp.app.*
import com.unircorn.csp.data.model.Examination
import com.unircorn.csp.databinding.FraExaminationBinding
import com.unircorn.csp.ui.base.BaseFra2
import com.unircorn.csp.ui.pagerAdapter.ExaminationPagerAdapter

class ExaminationFra : BaseFra2<FraExaminationBinding>() {

    override fun initViews()= with(binding) {
        super.initViews()
        tvTitle.text = "应知应会题库"
    }

    override fun initBindings() {
        super.initBindings()
        createExam()
    }

    private fun createExam() {
        api.createExaminationJustStudy()
            .lifeOnMain(this)
            .subscribe(
                {
                    if (it.failed) return@subscribe
                    this.examination = it.data
                    initViewPager2()
                },
                { it.errorMsg().toast() }
            )
    }

    private lateinit var examination: Examination

    private fun initViewPager2() = with(binding.ViewPager2) {
        val examinationPagerAdapter = ExaminationPagerAdapter(fragment = this@ExaminationFra, examination = examination)
        offscreenPageLimit = examinationPagerAdapter.itemCount - 1
        adapter = examinationPagerAdapter
        removeEdgeEffect()
    }


}