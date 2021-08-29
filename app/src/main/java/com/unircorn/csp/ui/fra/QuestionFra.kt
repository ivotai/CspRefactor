package com.unircorn.csp.ui.fra

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.unircorn.csp.app.*
import com.unircorn.csp.data.event.OptionSubmitEvent
import com.unircorn.csp.data.model.Question
import com.unircorn.csp.data.model.base.OptionSelector
import com.unircorn.csp.databinding.FraQuesionBinding
import com.unircorn.csp.ui.adapter.OptionAdapter
import com.unircorn.csp.ui.base.BaseFra2

class QuestionFra : BaseFra2<FraQuesionBinding>() {

    override fun initViews() = with(binding) {
        super.initViews()
        TvPosition.text = "${(position + 1)}"
        TvSize.text = "/${size}"
        TvName.text = question.name
        initRecyclerView()
    }

    private val optionAdapter = OptionAdapter()

    private fun initRecyclerView() = with(binding) {
        RvOption.run {
            layoutManager = LinearLayoutManager(context)
            adapter = optionAdapter
            optionAdapter.setList(question.optionList.map {
                it.questionId = question.questionId
                it.position = question.optionList.indexOf(it)
                return@map it
            }.map { OptionSelector(it) })
        }
    }

    override fun initBindings(): Unit = with(binding) {
        super.initBindings()
        btnSubmit.safeClicks().subscribe { submitX() }
    }

    private fun submitX() = with(binding) {
        val optionsSelected = optionAdapter.data.filter { it.isSelected }.map { it.option }
        if (optionsSelected.isEmpty()) {
            "还没选呢".toast()
            return
        }

        val optionsCorrect = optionAdapter.data.map { it.option }.filter { it.isCorrect }
        var isCorrect = true
        for (optionSelected in optionsSelected) {
            val index = optionsSelected.indexOf(optionSelected)
            isCorrect = optionSelected == optionsCorrect[index]
            if (!isCorrect) break
        }

        TvResult.text =
            "回答${if (isCorrect) "正确" else "错误"}，正确答案是: ${
                optionsCorrect.joinToString(",") { it.letter }
            }"
        TvResult.visibility = View.VISIBLE

        btnSubmit.isEnabled = false
        RxBus.post(OptionSubmitEvent(optionsSelected = optionsSelected))
    }

    private val question by lazy { requireArguments().getSerializable(Question) as Question }
    private val position by lazy { requireArguments().getInt(Position) }
    private val size by lazy { requireArguments().getInt(Size) }

}