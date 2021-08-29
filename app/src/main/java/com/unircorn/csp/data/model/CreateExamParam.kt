package com.unircorn.csp.data.model

data class SubmitExaminationParam(
    val answerList: List<Question>,
    val examinationId: String
)
