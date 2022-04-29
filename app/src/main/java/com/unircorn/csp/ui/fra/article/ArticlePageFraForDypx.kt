package com.unircorn.csp.ui.fra.article

import android.content.Intent
import com.unircorn.csp.app.SpecialId
import com.unircorn.csp.app.Title
import com.unircorn.csp.app.Training
import com.unircorn.csp.app.safeClicks
import com.unircorn.csp.data.model.Training
import com.unircorn.csp.ui.act.ExaminationAct
import com.unircorn.csp.ui.adapter.DypxAdapter
import com.unircorn.csp.ui.header.DypxFooterView

class ArticlePageFraForDypx : ArticlePageFra() {

    override fun initOperationBinding() {
        tvOperation.text = ""
//        tvOperation.safeClicks().subscribe {
//            startActivity(Intent(requireContext(), ExaminationAct::class.java).apply {
//                putExtra(SpecialId, category)
//                putExtra(Title, title)
//            })
//        }
    }

    override fun initPageAdapter() {
        super.initPageAdapter()
        pageAdapter.addFooterView(DypxFooterView(training = training))
    }

    private val training by lazy { requireArguments().getSerializable(Training) as Training }

}