package dev.rafaelsermenho.newsreader.model

import com.google.gson.annotations.SerializedName

data class ArticleList(
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Integer,
    @SerializedName("articles")
    val articles: List<Article>
)