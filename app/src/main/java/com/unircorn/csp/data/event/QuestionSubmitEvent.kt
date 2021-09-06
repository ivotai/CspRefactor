package com.unircorn.csp.data.event

import com.unircorn.csp.data.model.Option

data class QuestionSubmitEvent(val optionsSelected: List<Option>,val isCorrect:Boolean)