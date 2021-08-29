package com.unircorn.csp.data.model

data class Question(
    val description: String,
    val options: List<Option>,
    val answer: Int = -1,
    val rightAnswer: Int
)