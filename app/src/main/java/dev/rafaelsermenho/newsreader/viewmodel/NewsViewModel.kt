package dev.rafaelsermenho.newsreader.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import dev.rafaelsermenho.newsreader.model.Article
import dev.rafaelsermenho.newsreader.model.SourceList
import dev.rafaelsermenho.newsreader.repository.NewsReaderRepository

class NewsViewModel(private val repository: NewsReaderRepository) : ViewModel() {

    private var sourceList: LiveData<SourceList> = MutableLiveData<SourceList>()
    private var articleList: LiveData<List<Article>> = MutableLiveData<List<Article>>()


    fun getSources(category: String?): LiveData<SourceList> {
        if (sourceList.value == null) {
            sourceList = loadSourceList(category)
        }

        return sourceList
    }

    private fun loadSourceList(category: String?): LiveData<SourceList> {
        return repository.getSources(category)
    }

    fun getArticlesFrom(sourceName: String, page: Int): LiveData<List<Article>> {
        if (articleList.value.isNullOrEmpty()) {
            articleList = loadArticleListFrom(sourceName, page)
        }
        return articleList
    }

    private fun loadArticleListFrom(sourceId: String, page: Int): LiveData<List<Article>> {
        return repository.getArticlesFrom(sourceId, page)
    }

}
