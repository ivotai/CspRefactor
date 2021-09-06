package com.unircorn.csp.data.model

import java.io.Serializable

data class Question(
    val questionId: String,
    val name: String,
    val optionList: List<Option>,
    var options: List<String>? = null,
    var isCorrect: Boolean? = null
) : Serializable {

    val optionsCorrect: List<String> get() = optionList.filter { it.isCorrect }.map { it.optionId }
    val isSingleSelection: Boolean get() = optionsCorrect.size == 1

}