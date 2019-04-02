package dev.rafaelsermenho.newsreader.repository

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import dev.rafaelsermenho.newsreader.model.Article
import dev.rafaelsermenho.newsreader.model.ArticleList
import dev.rafaelsermenho.newsreader.model.SourceList
import dev.rafaelsermenho.newsreader.repository.api.NewsReaderApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsReaderRepository(private val newsReaderApi: NewsReaderApi) {
    fun getSources(category: String?): MutableLiveData<SourceList> {
        val call = newsReaderApi.getSources(category)
        val data = MutableLiveData<SourceList>()
        call.enqueue(object : Callback<SourceList> {
            override fun onFailure(call: Call<SourceList>, t: Throwable) {
                Log.e(NewsReaderApi::class.java.simpleName, "Error trying to retrieve sources")
                data.value = null
            }

            override fun onResponse(call: Call<SourceList>, response: Response<SourceList>) {
                Log.d("myTag", response.body().toString())
                data.value = response.body()
            }

        })
        return data
    }

    fun getArticlesFrom(sourceId: String, page: Int): MutableLiveData<List<Article>> {
        val call = newsReaderApi.getArticlesFrom(sourceId, page)
        val data = MutableLiveData<List<Article>>()
        var articleList: List<Article>? = ArrayList()
        call.enqueue(object : Callback<ArticleList> {
            override fun onFailure(call: Call<ArticleList>, t: Throwable) {
                Log.e(NewsReaderApi::class.java.simpleName, "Error trying to retrieve news")
                data.value = articleList
            }

            override fun onResponse(call: Call<ArticleList>, response: Response<ArticleList>) {
                if (response.isSuccessful) {
                    articleList = (response.body() as ArticleList).articles
                }
                data.value = articleList
            }
        })
        return data
    }
}