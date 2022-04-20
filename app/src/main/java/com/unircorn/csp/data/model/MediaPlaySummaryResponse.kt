package com.unircorn.csp.data.model

data class MediaPlaySummaryResponse(
    val category: List<String>,
    val date: List<String>,
    val value: List<List<Int>>
)