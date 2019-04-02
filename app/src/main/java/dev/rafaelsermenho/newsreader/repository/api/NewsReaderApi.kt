package dev.rafaelsermenho.newsreader.repository.api

import dev.rafaelsermenho.newsreader.model.ArticleList
import dev.rafaelsermenho.newsreader.model.SourceList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsReaderApi {

    @GET("sources")
    fun getSources(
        @Query("category") category: String?
        , @Query("language") language: String = "en"
    ): Call<SourceList>

    @GET("everything")
    fun getArticlesFrom(
        @Query("sources") source: String
        , @Query("page") page: Int
        , @Query("language") language: String = "en"
    ): Call<ArticleList>
}