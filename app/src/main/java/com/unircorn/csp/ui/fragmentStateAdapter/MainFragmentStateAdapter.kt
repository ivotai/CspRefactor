package com.unircorn.csp.ui.fragmentStateAdapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.unircorn.csp.app.Category
import com.unircorn.csp.ui.fra.TestFra

class MainFragmentStateAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount() = titles.size

    override fun createFragment(position: Int): Fragment = TestFra().apply {
        arguments = Bundle().apply {
            putString(Category, categories[position])
        }
    }

    companion object {
        val titles = listOf("学习教育", "学习讨论", "政策规定", "工作动态", "党史学习", "专题研究", "技术标准")
        val abbr = listOf("教育", "讨论", "政策", "工作", "党史", "专题", "技术")
        val categories = listOf("xxjy", "", "zcgd", "gzdt", "dsxx", "ztyj", "jsbz")
    }

}