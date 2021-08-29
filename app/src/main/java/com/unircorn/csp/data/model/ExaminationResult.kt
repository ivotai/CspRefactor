package com.unircorn.csp.data.model

data class ExaminationResult(
    val examinationId: String,
    val examinee: String,
    val finishTime: Long,
    val passed: Int,
    val passedScore: Int,
    val perfectScore: Int,
    val score: Int,
    val startTime: Long,
    val stats: Int
){
    val isPassed:Boolean get() = passed != 0
}