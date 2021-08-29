package com.unircorn.csp.data.model

import java.io.Serializable

data class Examination(
    val examinee: String,
    val questionList: List<Question>
) : Serializable