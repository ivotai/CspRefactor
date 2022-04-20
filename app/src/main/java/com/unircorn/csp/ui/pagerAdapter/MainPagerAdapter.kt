package com.unircorn.csp.ui.pagerAdapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.unircorn.csp.app.Category
import com.unircorn.csp.app.Title
import com.unircorn.csp.ui.StudyFra
import com.unircorn.csp.ui.fra.ExchangeFra
import com.unircorn.csp.ui.fra.HandbookFra
import com.unircorn.csp.ui.fra.article.ArticlePageFra

class MainPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount() = abbr.size

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> ExchangeFra()
        1 -> ArticlePageFra().apply {
            arguments = Bundle().apply {
                putString(Title, "政策规定")
                putString(Category, "zcgd")
            }
        }
        2 -> StudyFra()
        3 -> ArticlePageFra().apply {
            arguments = Bundle().apply {
                putString(Title, "信息工作动态")
                putString(Category, "gzdt")
            }
        }
        else -> HandbookFra()
    }

    companion object {
        val abbr = listOf("交流", "政策", "学习", "信息", "手边书")
    }

}