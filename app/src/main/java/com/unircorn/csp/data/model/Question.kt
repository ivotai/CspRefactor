package com.unircorn.csp.data.model

import java.io.Serializable

data class Question(
    val name: String,
    val optionList: List<Option>,
    val options: List<String> = ArrayList(),
) : Serializable