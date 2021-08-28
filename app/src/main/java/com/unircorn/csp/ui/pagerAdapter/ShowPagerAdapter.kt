package com.unircorn.csp.ui.pagerAdapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.unircorn.csp.ui.fra.topic.TopicFra

class ShowPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    companion object {
        val titles = listOf("学习小心得", "学习小视频", "学习小测试（应知应会）")
    }

    override fun getItemCount() = titles.size

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> TopicFra()
        1 -> TopicFra()
        else -> TopicFra()
    }

}