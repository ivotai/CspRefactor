package com.unircorn.csp.ui.fra

import android.content.Intent
import android.widget.TextView
import com.ruffian.library.widget.RConstraintLayout
import com.unircorn.csp.app.Category
import com.unircorn.csp.app.Title
import com.unircorn.csp.app.safeClicks
import com.unircorn.csp.databinding.FraHandbookBinding
import com.unircorn.csp.ui.act.article.HandbookArticleAct
import com.unircorn.csp.ui.base.BaseFra2

class HandbookFra : BaseFra2<FraHandbookBinding>() {

    private val mTitles = listOf(
        "机关作风", "三个规定", "\"两违\"\"两官\"", "立案信访", "审限管理",
        "减刑假释", "暂予监外执行", "民事审判", "破产管理", "行政审判",
        "执行管理"
    )
    private val mCategories = listOf(
        "sbs_jgzf", "sbs_sggd", "sbs_lwlg", "sbs_laxf", "sbs_sxgl",
        "sbs_jxjs", "sbs_zyjwzx", "sbs_mssp", "sbs_pcgl", "sbs_xzsp",
        "sbs_zxgl"
    )

    private lateinit var tvLabels: List<TextView>
    private lateinit var constraintLayouts: List<RConstraintLayout>


    override fun initViews() = with(binding) {
        tvLabels = listOf(
            tvLabel1, tvLabel2, tvLabel3, tvLabel4, tvLabel5,
            tvLabel6, tvLabel7, tvLabel8, tvLabel9, tvLabel10,
            tvLabel11
        )
        tvLabels.forEachIndexed { index, rTextView -> rTextView.text = mTitles[index] }
    }

    override fun initBindings() = with(binding) {
        constraintLayouts = listOf(
            constraintLayout1,
            constraintLayout2,
            constraintLayout3,
            constraintLayout4,
            constraintLayout5,
            constraintLayout6,
            constraintLayout7,
            constraintLayout8,
            constraintLayout9,
            constraintLayout10,
            constraintLayout11
        )
        constraintLayouts.forEachIndexed { index, rTextView ->
            rTextView.safeClicks().subscribe {
                startActivity(Intent(requireContext(), HandbookArticleAct::class.java).apply {
                    putExtra(Title, mTitles[index])
                    putExtra(Category, mCategories[index])
                })
            }
        }
    }

}