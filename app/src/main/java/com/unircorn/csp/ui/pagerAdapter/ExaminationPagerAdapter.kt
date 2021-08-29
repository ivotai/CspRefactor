package com.unircorn.csp.ui.pagerAdapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.unircorn.csp.data.model.Examination
import com.unircorn.csp.ui.fra.question.QuestionBankFra

class ExaminationPagerAdapter(fragment: Fragment, private val examination: Examination) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount() = examination.questionList.size

    override fun createFragment(position: Int): Fragment = when (position) {
        else -> QuestionBankFra()
    }

}