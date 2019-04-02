package dev.rafaelsermenho.newsreader.repository

import dev.rafaelsermenho.newsreader.BuildConfig
import dev.rafaelsermenho.newsreader.repository.api.NewsReaderApi
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInitializer {

    companion object {

        private fun setLoggingInterceptor(): HttpLoggingInterceptor {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            return logging
        }

        private fun getOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
            .addInterceptor {
                val original: Request = it.request()
                val request: Request = original.newBuilder()
                    .header("X-Api-Key", BuildConfig.NEWS_API_KEY)
                    .build()
                it.proceed(request)
            }
            .addInterceptor(setLoggingInterceptor())
            .build()


        private fun initRetrofit() = Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(getOkHttpClient())
            .build()

        fun getNewsReaderApi(): NewsReaderApi {
            return initRetrofit().create(NewsReaderApi::class.java)
        }
    }
}