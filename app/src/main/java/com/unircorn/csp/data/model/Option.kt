package com.unircorn.csp.data.model

import java.io.Serializable

data class Option(
    val correct: Int, // 1 正确答案
    val name: String,
    val optionId: String
): Serializable