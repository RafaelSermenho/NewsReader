package dev.rafaelsermenho.newsreader

import android.arch.lifecycle.MutableLiveData
import dev.rafaelsermenho.newsreader.model.Article
import dev.rafaelsermenho.newsreader.model.SourceList
import dev.rafaelsermenho.newsreader.repository.NewsReaderRepository
import dev.rafaelsermenho.newsreader.viewmodel.NewsViewModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.hamcrest.CoreMatchers.notNullValue
import org.junit.Assert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(JUnit4::class)
class ViewModelTests {

    private val newsReaderRepository = mockk<NewsReaderRepository>()
    private val newsViewModel = NewsViewModel(newsReaderRepository)

    @Test
    fun `when getSources is called with null category then a SourceList is returned`() {
        val sourceList = mockk<MutableLiveData<SourceList>>()
        every { newsReaderRepository.getSources(null) } returns sourceList
        assertThat(newsViewModel.getSources(null), notNullValue())
        verify { newsReaderRepository.getSources(null) }
    }

    @Test
    fun `when getSources is called with business category then a SourceList is returned`() {
        val sourceList = mockk<MutableLiveData<SourceList>>()
        every { newsReaderRepository.getSources("business") } returns sourceList
        assertThat(newsViewModel.getSources("business"), notNullValue())
        verify { newsReaderRepository.getSources("business") }
    }

    @Test
    fun `when getArticlesFrom is called with source and page then an ArticleList is returned`() {
        val articleList = mockk<MutableLiveData<List<Article>>>()
        every { newsReaderRepository.getArticlesFrom("abc-news", 1) } returns articleList
        assertThat(newsViewModel.getArticlesFrom("abc-news", 1), notNullValue())
        verify { newsReaderRepository.getArticlesFrom("abc-news", 1) }
    }
}
