package com.unircorn.csp.ui.pagerAdapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.unircorn.csp.ui.fra.question.QuestionBankFra
import com.unircorn.csp.ui.fra.topic.JustVideoTopicFra
import com.unircorn.csp.ui.fra.topic.TopicFra

class ShowPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    companion object {
        val titles = listOf("学习小心得", "学习小视频", "应知应会题库")
    }

    override fun getItemCount() = titles.size

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> TopicFra()
        1 -> JustVideoTopicFra()
        else -> QuestionBankFra()
    }

}