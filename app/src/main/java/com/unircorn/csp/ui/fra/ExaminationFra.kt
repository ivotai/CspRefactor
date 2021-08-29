package com.unircorn.csp.ui.fra

import com.afollestad.materialdialogs.MaterialDialog
import com.rxjava.rxlife.lifeOnMain
import com.unircorn.csp.app.*
import com.unircorn.csp.data.event.NextQuestionEvent
import com.unircorn.csp.data.event.OptionSubmitEvent
import com.unircorn.csp.data.model.Examination
import com.unircorn.csp.data.model.SubmitExaminationParam
import com.unircorn.csp.databinding.FraExaminationBinding
import com.unircorn.csp.ui.base.BaseFra2
import com.unircorn.csp.ui.pagerAdapter.ExaminationPagerAdapter

class ExaminationFra : BaseFra2<FraExaminationBinding>() {

    override fun initViews() = with(binding) {
        super.initViews()
        tvTitle.text = if (justStudy) "学习小园地" else "学习小测试"
    }

    override fun initBindings() {
        super.initBindings()
        getExamination()
    }

    private fun getExamination() {
        val single = if (justStudy) api.createExaminationJustStudy() else api.createExamination()
        single.lifeOnMain(this)
            .subscribe(
                {
                    if (it.failed) return@subscribe
                    this.examination = it.data
                    initViewPager2()
                },
                { it.errorMsg().toast() }
            )
    }

    override fun initEvents() = with(binding) {
        RxBus.registerEvent(this@ExaminationFra, OptionSubmitEvent::class.java, { event ->
            val question =
                examination.questionList.find { it.questionId == event.optionsSelected.first().questionId }!!
            question.options = event.optionsSelected.map { it.optionId }

            val finish = examination.questionList.all { it.options != null }
            if (finish) {
                if (justStudy) {
                    "您已学习完啦！".toast()
                } else {
                    submitExamination()
                }
            }
        })
        RxBus.registerEvent(this@ExaminationFra, NextQuestionEvent::class.java, {
            ViewPager2.setCurrentItem(ViewPager2.currentItem + 1, true)
        })
    }

    private fun submitExamination() {
        val param = SubmitExaminationParam(
            examinationId = examination.examinationId,
            answerList = examination.questionList
        )
        api.submitExamination(
            examinationId = examination.examinationId,
            submitExaminationParam = param
        ).lifeOnMain(this)
            .subscribe(
                {
                    if (it.failed) return@subscribe
                    val message = "总分: 100\r\n题目数量: ${examination.questionList.size}\r\n" +
                            "测试分数: ${it.data.score}\r\n测试结果: ${if (it.data.isPassed) "通过" else "不通过"}"
                    MaterialDialog(requireContext()).show {
                        title(text = "学习小测试")
                        message(text = message)
                        cancelOnTouchOutside(false)
                        positiveButton(text = "确认") { _ ->
                            finishAct()
                        }
                    }
                },
                { it.errorMsg().toast() }
            )
    }


    private lateinit var examination: Examination

    private fun initViewPager2() = with(binding.ViewPager2) {
        val examinationPagerAdapter =
            ExaminationPagerAdapter(fragment = this@ExaminationFra, examination = examination)
        offscreenPageLimit = examinationPagerAdapter.itemCount - 1
        adapter = examinationPagerAdapter
        removeEdgeEffect()
        isUserInputEnabled = false
    }


    private val justStudy by lazy { requireArguments().getBoolean(Param) }

}