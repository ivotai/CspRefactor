package com.unircorn.csp.ui.fra

import androidx.recyclerview.widget.LinearLayoutManager
import com.unircorn.csp.app.Position
import com.unircorn.csp.app.Question
import com.unircorn.csp.app.Size
import com.unircorn.csp.data.model.Question
import com.unircorn.csp.data.model.base.OptionSelector
import com.unircorn.csp.databinding.FraQuesionBinding
import com.unircorn.csp.ui.adapter.OptionAdapter
import com.unircorn.csp.ui.base.BaseFra2

class QuestionFra : BaseFra2<FraQuesionBinding>() {

    override fun initViews() = with(binding) {
        super.initViews()
        initRecyclerView()

        TvPosition.text = "${(position + 1)}"
        TvSize.text = "/${size}"
    }

    private val optionAdapter = OptionAdapter()

    private fun initRecyclerView() = with(binding) {
        RvOption.run {
            layoutManager = LinearLayoutManager(context)
            adapter = optionAdapter
            optionAdapter.setList(question.optionList.map {
                it.index = question.optionList.indexOf(it)
                return@map it
            }.map { OptionSelector(it) })
        }
    }

    private val question by lazy { requireArguments().getSerializable(Question) as Question }
    private val position by lazy { requireArguments().getInt(Position) }
    private val size by lazy { requireArguments().getInt(Size) }

}