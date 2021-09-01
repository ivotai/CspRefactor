package com.unircorn.csp.data.model

import java.io.Serializable

data class Question(
    val questionId: String,
    val name: String,
    val optionList: List<Option>,
    var options: List<String>? = null,
) : Serializable {

    val optionsCorrect: List<String> get() = optionList.filter { it.isCorrect }.map { it.optionId }

}