package com.unircorn.csp.ui.fra

import com.unircorn.csp.app.removeEdgeEffect
import com.unircorn.csp.data.model.TestProvider
import com.unircorn.csp.databinding.FraTestBinding
import com.unircorn.csp.ui.base.BaseFra2
import com.unircorn.csp.ui.pagerAdapter.TestPagerAdapter

class TestFra : BaseFra2<FraTestBinding>() {

    override fun initViews() {
        super.initViews()
        initViewPager2()
    }

    private val test = TestProvider.provider()

    private fun initViewPager2() = with(binding.ViewPager2) {
        removeEdgeEffect()
        val pagerAdapter = TestPagerAdapter(this@TestFra, test = test)
        offscreenPageLimit = pagerAdapter.itemCount - 1
        adapter = pagerAdapter
    }

}