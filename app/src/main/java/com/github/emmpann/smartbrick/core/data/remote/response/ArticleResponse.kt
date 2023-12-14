package com.github.emmpann.smartbrick.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class ArticleResponse(
    @field:SerializedName("success")
    val success: Boolean,

    @field:SerializedName("listArticle")
    val listArticle: List<Article>,

    @field:SerializedName("message")
    val message: String,
)