package com.unircorn.csp.ui.fragmentStateAdapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.unircorn.csp.app.Position
import com.unircorn.csp.ui.fra.ArticleFra
import com.unircorn.csp.ui.fra.topic.TopicFra

class MainFragmentStateAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount() = titles.size

    override fun createFragment(position: Int): Fragment {
        val fra = if (position == 1) TopicFra() else ArticleFra()
        fra.apply {
            arguments = Bundle().apply {
                putInt(Position, position)
            }
        }
        return fra
    }

    companion object {
        val titles = listOf("学习园地", "学习比一比", "政策规定", "信息工作动态", "党史学习")
        val abbr = listOf("学习", "比比", "政策", "信息", "党史")
        val categories = listOf("xxjy", "", "zcgd", "gzdt", "dsxx")
    }

}