package it.marvel

import android.app.Application
import it.marvel.network.MarvelRepository
import it.marvel.network.RetrofitBuilder
import timber.log.Timber

class App : Application() {

    companion object {
        internal lateinit var marvelRepository: MarvelRepository
    }

    override fun onCreate() {
        super.onCreate()
        marvelRepository = MarvelRepository(RetrofitBuilder.marvelAPI)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}