package com.unircorn.csp.ui.pagerAdapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.unircorn.csp.app.Category
import com.unircorn.csp.app.HideTitleLayout
import com.unircorn.csp.app.StudySmallVideo
import com.unircorn.csp.app.XueXiXiaoXinDe
import com.unircorn.csp.ui.fra.article.ArticlePageFra
import com.unircorn.csp.ui.fra.question.QuestionBankFra
import com.unircorn.csp.ui.fra.topic.TopicPageFra

class HandbookPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    companion object {
        val titles = listOf(
            "机关作风", "三个规定", "\"两违\"\"两官\"", "立案信访", "审限管理",
            "减刑假释", "暂予监外执行", "民事审判", "破产管理", "行政审判",
            "执行管理"
        )
        val categories = listOf(
            "xxjy", "xxjy", "xxjy", "xxjy", "xxjy",
            "xxjy", "xxjy", "xxjy", "xxjy", "xxjy",
            "xxjy"
        )
    }

    override fun getItemCount() = titles.size

    override fun createFragment(position: Int): Fragment = ArticlePageFra().apply {
        arguments = Bundle().apply {
            putString(Category, categories[position])
            putBoolean(HideTitleLayout, true)

        }
    }

}