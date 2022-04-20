package com.unircorn.csp.ui.fra

import com.google.android.material.tabs.TabLayoutMediator
import com.unircorn.csp.databinding.FraExchangeBinding
import com.unircorn.csp.ui.base.BaseFra2
import com.unircorn.csp.ui.pagerAdapter.ExchangePagerAdapter

class ExchangeFra : BaseFra2<FraExchangeBinding>() {

    override fun initViews() = with(binding) {
        tvTitle.text = "交流"
        initTabLayoutMediator()
    }

    private fun initTabLayoutMediator() = with(binding) {
        viewPager2.adapter = ExchangePagerAdapter(this@ExchangeFra)
        viewPager2.offscreenPageLimit = ExchangePagerAdapter.titles.size - 1
        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            tab.text = ExchangePagerAdapter.titles[position]
        }.attach()
    }

}