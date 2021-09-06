package com.unircorn.csp.data.model

import java.io.Serializable

data class Option(
    val correct: Int, // 1 正确答案
    val name: String,
    val optionId: String,

    var questionId: String?,
    var position: Int
) : Serializable {

    companion object {
        private val letters = listOf("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L")
    }

    val letter: String get() = letters[position]
    val isCorrect: Boolean get() = correct == 1
}