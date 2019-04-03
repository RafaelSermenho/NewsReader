import dev.rafaelsermenho.newsreader.model.SourceList
import dev.rafaelsermenho.newsreader.repository.api.NewsReaderApi
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Okio
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
    fun getSources() {
        enqueueResponse("responses/source.json")

        repository.getSources(null) as SourceList


//        val yigit = (getValue(repository.getUser("yigit")) as ApiSuccessResponse).body
//
        val request = mockWebServer.takeRequest()
//        assertThat(request.path, `is`("/users/yigit"))
//
//        assertThat<User>(yigit, notNullValue())
//        assertThat(yigit.avatarUrl, `is`("https://avatars3.githubusercontent.com/u/89202?v=3"))
//        assertThat(yigit.company, `is`("Google"))
//        assertThat(yigit.blog, `is`("birbit.com"))
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
