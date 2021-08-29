package com.unircorn.csp.ui.fra

import androidx.recyclerview.widget.LinearLayoutManager
import com.unircorn.csp.app.Position
import com.unircorn.csp.app.Question
import com.unircorn.csp.data.model.Question
import com.unircorn.csp.databinding.FraQuesionBinding
import com.unircorn.csp.ui.adapter.OptionAdapter
import com.unircorn.csp.ui.base.BaseFra2

class QuestionFra : BaseFra2<FraQuesionBinding>() {

    override fun initViews() {
        super.initViews()
        initRecyclerView()
    }

    private val optionAdapter = OptionAdapter()

    private fun initRecyclerView() = with(binding) {
        RvOption.run {
            layoutManager = LinearLayoutManager(context)
            adapter = optionAdapter
            optionAdapter.setList(question.optionList.map {
                it.index = question.optionList.indexOf(it)
                return@map it
            })
        }
    }

    private val question by lazy { requireArguments().getSerializable(Question) as Question }
    private val position by lazy { requireArguments().getInt(Position) }

}