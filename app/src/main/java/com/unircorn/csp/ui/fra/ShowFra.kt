package com.unircorn.csp.ui.fra

import com.google.android.material.tabs.TabLayoutMediator
import com.unircorn.csp.databinding.FraShowBinding
import com.unircorn.csp.ui.base.BaseFra2
import com.unircorn.csp.ui.pagerAdapter.MainFraStateAdapter
import com.unircorn.csp.ui.pagerAdapter.ShowPagerAdapter

class ShowFra : BaseFra2<FraShowBinding>() {

    override fun initViews() = with(binding) {
        tvTitle.text = MainFraStateAdapter.titles[1]
        initTabLayoutMediator()
    }

    private fun initTabLayoutMediator() = with(binding) {
        viewPager2.adapter = ShowPagerAdapter(this@ShowFra)
        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            tab.text = ShowPagerAdapter.titles[position]
        }.attach()
    }

}