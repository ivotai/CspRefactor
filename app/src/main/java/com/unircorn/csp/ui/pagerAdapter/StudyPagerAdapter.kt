package com.unircorn.csp.ui.pagerAdapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.unircorn.csp.app.Category
import com.unircorn.csp.app.HideTitleLayout
import com.unircorn.csp.app.Title
import com.unircorn.csp.ui.fra.article.ArticlePageFra

class StudyPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    companion object {
        val titles = listOf("党员培训", "日常学习", "党史学习")
    }

    override fun getItemCount() = titles.size

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> ArticlePageFra().apply {
            arguments = Bundle().apply {
                putString(Category, "dypx")
                putBoolean(HideTitleLayout, true)
            }
        }
        1 -> ArticlePageFra().apply {
            arguments = Bundle().apply {
                putString(Category, "rcxx")
                putBoolean(HideTitleLayout, true)
            }
        }
        else -> ArticlePageFra().apply {
            arguments = Bundle().apply {
                putString(Category, "dsxx")
                putBoolean(HideTitleLayout, true)
            }
        }
    }

}