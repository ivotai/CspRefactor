package com.unircorn.csp.ui.fra

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding4.view.clicks
import com.unircorn.csp.app.*
import com.unircorn.csp.data.event.NextQuestionEvent
import com.unircorn.csp.data.event.QuestionSubmitEvent
import com.unircorn.csp.data.model.Question
import com.unircorn.csp.data.model.base.OptionSelector
import com.unircorn.csp.databinding.FraQuesionBinding
import com.unircorn.csp.ui.adapter.OptionAdapter
import com.unircorn.csp.ui.base.BaseFra2
import kotlin.math.min

class QuestionFra : BaseFra2<FraQuesionBinding>() {

    override fun initViews() = with(binding) {
        super.initViews()
        TvLabel.text = if (question.isSingleSelection) "单选" else "多选"
        TvPosition.text = "${(position + 1)}"
        TvSize.text = "/${size}"
        TvName.text = question.name
        initRecyclerView()
    }

    lateinit var optionAdapter: OptionAdapter

    private fun initRecyclerView() = with(binding) {
        RvOption.run {
            layoutManager = LinearLayoutManager(context)
            optionAdapter = OptionAdapter(question)
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
        btnSubmit.clicks().subscribe {
            if (optionAdapter.isQuestionSubmit) {
                RxBus.post(NextQuestionEvent())
            } else {
                submitX()
            }
        }
    }

    private fun submitX() = with(binding) {
        val optionsSelected = optionAdapter.data.filter { it.isSelected }.map { it.option }
        if (optionsSelected.isEmpty()) {
            "至少选择一项".toast()
            return
        }

        val optionsCorrect = optionAdapter.data.map { it.option }.filter { it.isCorrect }

        // 可能正确，进一步确认
        var isCorrect = optionsSelected.size == optionsCorrect.size
        if (isCorrect) {
            // 额 两个 size 一样
            val size = min(optionsSelected.size, optionsCorrect.size)
            for (i in 0 until size) {
                isCorrect = optionsSelected[i].optionId == optionsCorrect[i].optionId
                if (!isCorrect) break
            }
        }

        TvResult.text =
            if (isCorrect) "回答正确" else "回答错误，正确答案是: ${optionsCorrect.joinToString(",") { it.letter }}"
        TvResult.visibility = View.VISIBLE

        btnSubmit.text = "下一题"
        optionAdapter.isQuestionSubmit = true
        RxBus.post(QuestionSubmitEvent(optionsSelected = optionsSelected, isCorrect = isCorrect))
    }

    private val question by lazy { requireArguments().getSerializable(Question) as Question }
    private val position by lazy { requireArguments().getInt(Position) }
    private val size by lazy { requireArguments().getInt(Size) }

}