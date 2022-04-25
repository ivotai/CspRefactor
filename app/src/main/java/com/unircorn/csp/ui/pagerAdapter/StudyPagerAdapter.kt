package com.unircorn.csp.ui.pagerAdapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.unircorn.csp.app.Category
import com.unircorn.csp.app.HideTitleLayout
import com.unircorn.csp.ui.fra.DypxFra
import com.unircorn.csp.ui.fra.article.ArticlePageFra

class StudyPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    companion object {
        val titles = listOf("党员培训", "党史课堂", "学习前沿")
    }

    override fun getItemCount() = titles.size

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> DypxFra()
        1 -> ArticlePageFra().apply {
            arguments = Bundle().apply {
                putString(Category, "dskt")
                putBoolean(HideTitleLayout, true)
            }
        }
        else -> ArticlePageFra().apply {
            arguments = Bundle().apply {
                putString(Category, "xxqy")
                putBoolean(HideTitleLayout, true)
            }
        }
    }

}