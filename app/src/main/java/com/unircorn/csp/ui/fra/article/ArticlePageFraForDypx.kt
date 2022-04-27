package com.unircorn.csp.ui.fra.article

import android.content.Intent
import com.unircorn.csp.app.Param
import com.unircorn.csp.app.safeClicks
import com.unircorn.csp.ui.act.ExaminationAct

class ArticlePageFraForDypx : ArticlePageFra() {

    override fun initOperationBinding() {
        tvOperation.text = "测试"
        tvOperation.safeClicks().subscribe {
            startActivity(Intent(requireContext(), ExaminationAct::class.java).apply {
                putExtra(Param, category)
            })
        }
    }
}