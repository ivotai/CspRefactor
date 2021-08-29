package com.unircorn.csp.data.event

import com.unircorn.csp.data.model.Option

data class OptionSubmitEvent(val optionsSelected: List<Option>)