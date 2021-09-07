package com.unircorn.csp.ui.pagerAdapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.unircorn.csp.app.Category
import com.unircorn.csp.app.Title
import com.unircorn.csp.ui.fra.StudyFra
import com.unircorn.csp.ui.fra.article.ArticlePageFra

class MainPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount() = abbr.size

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> StudyFra()
        1 -> ArticlePageFra().apply {
            arguments = Bundle().apply {
                putString(Title, "政策规定")
                putString(Category, "zcgd")
            }
        }
        2 -> ArticlePageFra().apply {
            arguments = Bundle().apply {
                putString(Title, "信息工作动态")
                putString(Category, "gzdt")
            }
        }
        else -> ArticlePageFra().apply {
            arguments = Bundle().apply {
                putString(Title, "党史学习")
                putString(Category, "dsxx")
            }
        }
    }

    companion object {
        val abbr = listOf("学习", "政策", "信息", "党史")
    }

}