package it.marvel.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import it.marvel.network.repository.MarvelRepository
import it.marvel.network.data.local.AppDatabase
import it.marvel.network.data.local.LocalDataSource
import it.marvel.network.data.remote.MarvelAPI
import it.marvel.network.data.remote.RemoteDataSource
import it.marvel.ui.home.HomeViewModel
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
}

val databaseModule = module {

    fun provideDatabase(context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, "characters_db")
            .fallbackToDestructiveMigration()
            .build()

    fun provideDao(database: AppDatabase) = database.characterDao()

    single { provideDatabase(androidContext()) }
    single { provideDao(get()) }
}

val apiModule = module {

    fun provideUserApi(retrofit: Retrofit) = retrofit.create(MarvelAPI::class.java)

    single { provideUserApi(get()) }
}

val networkModule = module {

    fun provideCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    fun getURL() = "https://gateway.marvel.com"
    fun getNetworkTimeout() = 10L

    fun provideMoshi() =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    fun provideHttpClient() =
        OkHttpClient
            .Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .connectTimeout(getNetworkTimeout(), TimeUnit.SECONDS)
            .build()

    fun provideRetrofit(moshi: Moshi, httpClient: OkHttpClient) =
        Retrofit.Builder()
            .baseUrl(getURL())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(httpClient)
            .build()

    single { provideCache(androidApplication()) }
    single { provideMoshi() }
    single { provideHttpClient() }
    single { provideRetrofit(get(), get()) }
}

val repositoryModule = module {

    fun provideRemoteRepository(marvelAPI: MarvelAPI) =
        RemoteDataSource(marvelAPI)

    fun provideMarvelRepository(marvelAPI: MarvelAPI, localDataSource: LocalDataSource) =
        MarvelRepository(
            remoteDataSource = provideRemoteRepository(marvelAPI),
            localDataSource = localDataSource
        )

    single { provideRemoteRepository(get()) }
    single { provideMarvelRepository(get(), get()) }
}