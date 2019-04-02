package dev.rafaelsermenho.newsreader

import android.app.Application
import dev.rafaelsermenho.newsreader.di.appModule
import org.koin.android.ext.android.startKoin

class NewsReaderApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(appModule))
    }
}