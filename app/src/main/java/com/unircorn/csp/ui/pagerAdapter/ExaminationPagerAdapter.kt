package com.unircorn.csp.ui.pagerAdapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.unircorn.csp.app.Position
import com.unircorn.csp.app.Question
import com.unircorn.csp.app.Size
import com.unircorn.csp.data.model.Examination
import com.unircorn.csp.ui.fra.QuestionFra

class ExaminationPagerAdapter(fragment: Fragment, private val examination: Examination) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount() = examination.questionList.size

    override fun createFragment(position: Int): Fragment = QuestionFra().apply {
        arguments = Bundle().apply {
            putSerializable(Position, position)
            putSerializable(Size, examination.questionList.size)
            putSerializable(Question, examination.questionList[position])
        }
    }

}