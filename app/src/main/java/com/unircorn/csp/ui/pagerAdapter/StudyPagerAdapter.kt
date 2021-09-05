package com.unircorn.csp.ui.pagerAdapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.unircorn.csp.app.Category
import com.unircorn.csp.app.HideTitleLayout
import com.unircorn.csp.ui.fra.article.ArticlePageFra
import com.unircorn.csp.ui.fra.question.QuestionBankFra
import com.unircorn.csp.ui.fra.topic.JustVideoTopicFra

class StudyPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    companion object {
        val titles = listOf("学习小园地", "学习小心得", "学习小视频", "学习小测试")
    }

    override fun getItemCount() = titles.size

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> ArticlePageFra().apply {
            arguments = Bundle().apply {
                putString(Category, "xxjy")
                putBoolean(HideTitleLayout, true)

            }
        }
        1 -> JustVideoTopicFra()
        2 -> ArticlePageFra().apply {
            arguments = Bundle().apply {
                putString(Category, "xxsp")
                putBoolean(HideTitleLayout, true)
            }
        }
        else -> QuestionBankFra()
    }

}