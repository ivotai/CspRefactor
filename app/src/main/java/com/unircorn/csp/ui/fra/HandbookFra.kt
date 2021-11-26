package com.unircorn.csp.ui.fra

import android.content.Intent
import com.ruffian.library.widget.RTextView
import com.unircorn.csp.app.Category
import com.unircorn.csp.app.Title
import com.unircorn.csp.app.safeClicks
import com.unircorn.csp.databinding.FraHandbookBinding
import com.unircorn.csp.ui.act.article.HandbookArticleAct
import com.unircorn.csp.ui.base.BaseFra2

class HandbookFra : BaseFra2<FraHandbookBinding>() {

    private val titles = listOf(
        "机关作风", "三个规定", "\"两违\"\"两官\"", "立案信访", "审限管理",
        "减刑假释", "暂予监外执行", "民事审判", "破产管理", "行政审判",
        "执行管理"
    )
    private val mCategories = listOf(
        "sbs_jgzf", "sbs_sggd", "sbs_lwlg", "sbs_laxf", "sbs_sxgl",
        "sbs_jxjs", "sbs_zyjwzx", "sbs_mssp", "sbs_pcgl", "sbs_xzsp",
        "sbs_zxgl"
    )

    private lateinit var tvLabels: List<RTextView>

    override fun initViews() = with(binding) {
        tvLabels = listOf(tvLabel1, tvLabel2, tvLabel3, tvLabel4)
        tvLabels.forEachIndexed { index, rTextView ->
            rTextView.text = titles[index]
        }
    }

    override fun initBindings() {
        tvLabels.forEachIndexed { index, rTextView ->
            rTextView.safeClicks().subscribe {
                startActivity(Intent(requireContext(), HandbookArticleAct::class.java).apply {
                    putExtra(Title, titles[index])
                    putExtra(Category, mCategories[index])
                })
            }
        }
    }

}