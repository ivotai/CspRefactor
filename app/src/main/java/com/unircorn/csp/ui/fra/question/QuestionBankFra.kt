package com.unircorn.csp.ui.fra.question

import android.content.Intent
import com.unircorn.csp.app.Category
import com.unircorn.csp.app.TKXX
import com.unircorn.csp.app.Title
import com.unircorn.csp.app.safeClicks
import com.unircorn.csp.databinding.FraQuestionBankBinding
import com.unircorn.csp.ui.act.ExaminationAct
import com.unircorn.csp.ui.act.article.ArticlePageAct
import com.unircorn.csp.ui.base.BaseFra2

class QuestionBankFra : BaseFra2<FraQuestionBankBinding>() {

    override fun initBindings(): Unit = with(binding) {
        super.initBindings()
        // 题库学习
        constraintLayout1.safeClicks().subscribe {
            startActivity(Intent(requireContext(), ExaminationAct::class.java).apply {
                putExtra(TKXX, true)
            })
        }
        // 随机测试
        constraintLayout2.safeClicks().subscribe {
            startActivity(Intent(requireContext(), ExaminationAct::class.java).apply {
                putExtra(Title, "随机测试")
            })
        }
        // 应知应会
        constraintLayout3.safeClicks().subscribe {
            startActivity(Intent(requireContext(), ArticlePageAct::class.java).apply {
                putExtra(Title, "应知应会")
                putExtra(Category, "yzyh")
            })
        }
    }

}
