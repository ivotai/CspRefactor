package com.unircorn.csp.ui.fraStateAdapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.unircorn.csp.app.Param
import com.unircorn.csp.ui.fra.article.ArticleFra
import com.unircorn.csp.ui.fra.topic.TopicFra

class MainFraStateAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount() = titles.size

    override fun createFragment(position: Int): Fragment =
        if (position == 1) TopicFra() else ArticleFra().apply {
            arguments = Bundle().apply {
                putInt(Param, position)
            }
        }

    companion object {
        val titles = listOf("学习园地", "学习比一比", "政策规定", "信息工作动态", "党史学习")
        val abbr = listOf("学习", "比比", "政策", "信息", "党史")
        val categories = listOf("xxjy", "", "zcgd", "gzdt", "dsxx")
    }

}