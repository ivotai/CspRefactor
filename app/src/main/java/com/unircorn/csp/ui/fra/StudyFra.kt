package com.unircorn.csp.ui.fra

import com.google.android.material.tabs.TabLayoutMediator
import com.unircorn.csp.databinding.FraStudyBinding
import com.unircorn.csp.ui.base.BaseFra2
import com.unircorn.csp.ui.pagerAdapter.StudyPagerAdapter

class StudyFra : BaseFra2<FraStudyBinding>() {

    override fun initViews() = with(binding) {
        tvTitle.text = "学习"
        initTabLayoutMediator()
    }

    private fun initTabLayoutMediator() = with(binding) {
        viewPager2.adapter = StudyPagerAdapter(this@StudyFra)
        viewPager2.offscreenPageLimit = StudyPagerAdapter.titles.size - 1
        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            tab.text = StudyPagerAdapter.titles[position]
        }.attach()
    }

}