package it.marvel

import android.app.Application
import it.marvel.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initTimber()
        initKoinModules()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initKoinModules() =
        startKoin {
            androidContext(this@App)
            modules(
                viewModelModule,
                apiModule,
                networkModule,
                databaseModule,
                repositoryModule,
            )
        }
}