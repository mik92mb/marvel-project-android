package it.marvel.network

import com.squareup.moshi.FromJson
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitBuilder {

    private const val BASE_URL = "https://gateway.marvel.com"
    private const val NETWORK_TIMEOUT = 10L

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .add(Iso8601LocalDateTimeTypeAdapter())
        .build()

    val marvelAPI: MarvelAPI = provideRetrofit().create(MarvelAPI::class.java)

    private fun provideHttpClient() =
        OkHttpClient
            .Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .connectTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
            .build()

    private fun provideRetrofit() =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(provideHttpClient())
            .build()

    private class Iso8601LocalDateTimeTypeAdapter {

        @FromJson
        fun fromJson(string: String) =
            Instant.parse(string).atZone(ZoneId.systemDefault()).toLocalDateTime()

    }
}