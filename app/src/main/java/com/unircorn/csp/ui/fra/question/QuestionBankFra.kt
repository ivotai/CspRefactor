package com.unircorn.csp.ui.fra.question

import android.content.Intent
import com.unircorn.csp.app.Category
import com.unircorn.csp.app.Param
import com.unircorn.csp.app.Title
import com.unircorn.csp.app.safeClicks
import com.unircorn.csp.databinding.FraQuestionBankBinding
import com.unircorn.csp.ui.act.TestAct
import com.unircorn.csp.ui.act.article.ArticlePageAct
import com.unircorn.csp.ui.base.BaseFra2

class QuestionBankFra : BaseFra2<FraQuestionBankBinding>() {

    override fun initBindings(): Unit = with(binding) {
        super.initBindings()
        constraintLayout1.safeClicks().subscribe { startTestAct(justStudy = true) }
        constraintLayout2.safeClicks().subscribe { startTestAct(justStudy = false) }
        constraintLayout3.safeClicks().subscribe {
            startActivity(Intent(requireContext(), ArticlePageAct::class.java).apply {
                putExtra(Title, "应知应会")
                putExtra(Category, "yzyh")
            })
        }
    }

    private fun startTestAct(justStudy: Boolean) {
        Intent(requireContext(), TestAct::class.java).apply {
            putExtra(Param, justStudy)
        }.let { startActivity(it) }
    }

}
