package com.unircorn.csp.data.model

import java.io.Serializable

data class Option(
    val correct: Int, // 1 正确答案
    val name: String,
    val optionId: String,
    var index: Int = -1
) : Serializable {

    companion object {
        private val letters = listOf("A", "B", "C", "D")
    }

     val letter: String get() = letters[index]

}