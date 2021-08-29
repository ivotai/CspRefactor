package com.unircorn.csp.data.model.base

import com.unircorn.csp.data.model.Option

data class OptionSelector(
    val option: Option,
    var isSelected: Boolean = false
)