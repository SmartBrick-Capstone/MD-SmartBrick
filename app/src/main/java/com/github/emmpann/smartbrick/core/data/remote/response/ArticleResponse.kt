package com.github.emmpann.smartbrick.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class ArticleResponse(
    @field:SerializedName("listArticle")
    val listArticle: List<Article>,

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)

data class Article(
    val id: String,

    val photoUrl: String,

    val title: String,

    val description: String,

    val date: String
)