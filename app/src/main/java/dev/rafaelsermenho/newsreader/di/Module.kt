package dev.rafaelsermenho.newsreader.di

import dev.rafaelsermenho.newsreader.repository.NewsReaderRepository
import dev.rafaelsermenho.newsreader.repository.RetrofitInitializer
import dev.rafaelsermenho.newsreader.viewmodel.NewsViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val appModule = module {
    single { RetrofitInitializer.getNewsReaderApi() }
    single { NewsReaderRepository(get()) }
    viewModel { NewsViewModel(get()) }
}