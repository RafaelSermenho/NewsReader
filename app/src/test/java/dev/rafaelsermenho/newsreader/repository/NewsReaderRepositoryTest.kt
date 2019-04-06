import dev.rafaelsermenho.newsreader.model.Article
import dev.rafaelsermenho.newsreader.model.ArticleList
import dev.rafaelsermenho.newsreader.model.SourceList
import dev.rafaelsermenho.newsreader.repository.api.NewsReaderApi
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Okio
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
class GithubServiceTest {

    private lateinit var repository: NewsReaderApi

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun createService() {
        mockWebServer = MockWebServer()
        repository = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsReaderApi::class.java)
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun `when no category is selected then all sources should be retrieved`() {
        enqueueResponse("responses/sources_all_categories.json")

        val sourceList = (repository.getSources(null).execute().body() as SourceList)
        assertNotNull(sourceList)
        assertEquals(6, sourceList.sources.count())
    }

    @Test
    fun `when a category is selected then only sources from that category should be retrieved`() {
        enqueueResponse("responses/sources_one_category.json")
        val sourceList = (repository.getSources("technology").execute().body() as SourceList)
        assertNotNull(sourceList)
        assertEquals(1, sourceList.sources.count())
        assertThat(sourceList.sources.count(), `is`(1))
        assertThat(sourceList.sources[0].category, `is`("technology"))
        assertThat(sourceList.sources[0].name, `is`("Ars Technica"))
    }


    @Test
    fun `when an invalid category is selected then an empty source list is retrieved`() {
        enqueueResponse("responses/sources_invalid_category.json")
        val sourceList = (repository.getSources("technology").execute().body() as SourceList)
        assertNotNull(sourceList)
        assertEquals(0, sourceList.sources.count())
    }

    @Test
    fun `when a source is selected then all articles from that source should be retrieved`() {
        enqueueResponse("responses/articles_page_one.json")
        val articleList = (repository.getArticlesFrom("abc-news", 1).execute().body() as ArticleList)
        assertNotNull(articleList)
        assertEquals(20, articleList.articles.count())
    }

    @Test
    fun `when I want to retrieve 2nd page results then all articles from second page should be retrieved and added to list`() {
        enqueueResponse("responses/articles_page_one.json")
        enqueueResponse("responses/articles_page_two.json")
        val articles = ArrayList<Article>()
        var articleList = repository.getArticlesFrom("abc-news", 1).execute().body() as ArticleList
        articles.addAll(articleList.articles)
        articleList = repository.getArticlesFrom("abc-news", 2).execute().body() as ArticleList
        articles.addAll(articleList.articles)
        assertNotNull(articles)
        assertEquals(40, articles.count())
    }


    private fun enqueueResponse(fileName: String, headers: Map<String, String> = emptyMap()) {
        val inputStream = this.javaClass.classLoader.getResourceAsStream(fileName)
        val source = Okio.buffer(Okio.source(inputStream))
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(
            mockResponse
                .setBody(source.readString(Charsets.UTF_8))
        )
    }
}
