package com.unircorn.csp.data.model

data class Examination(
    val examinationId: String,
    val passedScore: Int,
    val perfectScore: Int,
    val startTime: Long,
    val stats: Int,
    val examinee: String,
    var questionList: List<Question>
)
