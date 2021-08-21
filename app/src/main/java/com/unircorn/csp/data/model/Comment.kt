package com.unircorn.csp.data.model

data class Comment(
    val content: String,
    val issueTime: Long,
    val issuer: String,
    val objectId: String
)

data class CreateCommentParam(val content:String)