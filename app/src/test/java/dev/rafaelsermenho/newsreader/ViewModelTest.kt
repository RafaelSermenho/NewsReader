package dev.rafaelsermenho.newsreader

import dev.rafaelsermenho.newsreader.repository.NewsReaderRepository
import dev.rafaelsermenho.newsreader.viewmodel.NewsViewModel
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
    fun testNull() {
        assertThat(newsViewModel.getSources(null), notNullValue())
        verify { newsReaderRepository.getSources(null) }
    }
}
