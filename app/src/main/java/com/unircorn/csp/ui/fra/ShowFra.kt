package com.unircorn.csp.ui.fra

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.unircorn.csp.databinding.FraShowBinding
import com.unircorn.csp.ui.base.BaseFra
import com.unircorn.csp.ui.pagerAdapter.MainFraStateAdapter
import com.unircorn.csp.ui.pagerAdapter.ShowPagerAdapter

class ShowFra : BaseFra() {

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

//

    private var _binding: FraShowBinding? = null

    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FraShowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}