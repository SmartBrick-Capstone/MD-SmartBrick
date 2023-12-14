package com.github.emmpann.smartbrick.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class Article(

    @field:SerializedName("date")
    val date: String,

    @field:SerializedName("image")
    val image: String,

    @field:SerializedName("updated_at")
    val updatedAt: String,

    @field:SerializedName("author")
    val author: String,

    @field:SerializedName("publisher")
    val publisher: String,

    @field:SerializedName("created_at")
    val createdAt: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("slug")
    val slug: String,

    @field:SerializedName("content")
    val content: String,
)