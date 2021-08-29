package com.unircorn.csp.ui.pagerAdapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.unircorn.csp.data.model.Test
import com.unircorn.csp.ui.fra.question.QuestionBankFra
import com.unircorn.csp.ui.fra.topic.TopicFra

class TestPagerAdapter(fragment: Fragment, private val test: Test) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount() = test.questions.size

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> TopicFra()
        else -> QuestionBankFra()
    }

}