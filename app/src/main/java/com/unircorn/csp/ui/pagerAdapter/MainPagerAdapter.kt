package com.unircorn.csp.ui.pagerAdapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.unircorn.csp.app.Param
import com.unircorn.csp.ui.fra.ShowFra
import com.unircorn.csp.ui.fra.StudyFra
import com.unircorn.csp.ui.fra.article.ArticleFra

class MainPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount() = titles.size

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> StudyFra()
        1 -> ShowFra()
        else -> ArticleFra().apply {
            arguments = Bundle().apply {
                putInt(Param, position)
            }
        }
    }

    companion object {
        val titles = listOf("学习园地", "晒一晒", "政策规定", "信息工作动态", "党史学习")
        val abbr = listOf("学习", "晒晒", "政策", "信息", "党史")
        val categories = listOf("xxjy", "", "zcgd", "gzdt", "dsxx")
    }

}