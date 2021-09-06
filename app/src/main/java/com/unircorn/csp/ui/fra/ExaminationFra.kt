package com.unircorn.csp.ui.fra

import com.afollestad.materialdialogs.MaterialDialog
import com.rxjava.rxlife.lifeOnMain
import com.unircorn.csp.app.*
import com.unircorn.csp.data.event.NextQuestionEvent
import com.unircorn.csp.data.event.QuestionSubmitEvent
import com.unircorn.csp.data.model.Examination
import com.unircorn.csp.data.model.SubmitExaminationParam
import com.unircorn.csp.databinding.FraExaminationBinding
import com.unircorn.csp.ui.base.BaseFra2
import com.unircorn.csp.ui.pagerAdapter.ExaminationPagerAdapter

class ExaminationFra : BaseFra2<FraExaminationBinding>() {

    override fun initViews() = with(binding) {
        super.initViews()
        tvTitle.text = if (justStudy) "题库学习" else "随机测试"
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

                    // 排序，先单选后多选
                    if (!justStudy) {
                        this.examination.questionList =
                            examination.questionList.sortedBy { question -> !question.isSingleSelection }
                    }

                    initViewPager2()
                },
                { it.errorMsg().toast() }
            )
    }

    override fun initEvents() = with(binding) {
        RxBus.registerEvent(this@ExaminationFra, QuestionSubmitEvent::class.java, { event ->
            val question =
                examination.questionList.find { it.questionId == event.optionsSelected.first().questionId }!!
            question.options = event.optionsSelected.map { it.optionId }
            question.isCorrect = event.isCorrect

            //
            val questionsAnswered = examination.questionList.filter { it.options != null }
            val questionsCorrect = questionsAnswered.filter { it.isCorrect!! }

            RoundCornerProgressBar.max = questionsAnswered.size.toFloat()
            RoundCornerProgressBar.progress = questionsCorrect.size.toFloat()
            tvCorrect.text = questionsCorrect.size.toString()
            tvWrong.text = (questionsAnswered.size - questionsCorrect.size).toString()

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
        // 这句话不使用看看
//        offscreenPageLimit = examinationPagerAdapter.itemCount - 1
        adapter = examinationPagerAdapter
        removeEdgeEffect()
        isUserInputEnabled = false
    }


    private val justStudy by lazy { requireArguments().getBoolean(Param) }

}