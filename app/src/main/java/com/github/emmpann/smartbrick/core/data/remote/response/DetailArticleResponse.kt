package com.github.emmpann.smartbrick.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class DetailArticleResponse(

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("listArticle")
	val listArticle: Article,

	@field:SerializedName("message")
	val message: String
)