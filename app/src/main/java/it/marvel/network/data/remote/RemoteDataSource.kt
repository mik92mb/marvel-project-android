package it.marvel.network.data.remote

import it.marvel.network.utils.State
import it.marvel.network.utils.Support
import retrofit2.Response
import timber.log.Timber

class RemoteDataSource(private val marvelAPI: MarvelAPI) {

    suspend fun getCharacters() = getResult {
        val timestamp = System.currentTimeMillis().toString()
        marvelAPI.getCharacters(
            apiKey = Support.PUBLIC_API_KEY,
            timestamp = timestamp,
            hash = Support.hashGenerator(timestamp),
            limit = 100
        )
    }

}

suspend fun <T> getResult(call: suspend () -> Response<T>): State<T> {
    try {
        val response = call()
        if (response.isSuccessful) {
            val body = response.body()
            body?.let {
                return State.Success(body)
            } ?: return State.Error(NullPointerException(response.message()))
        }
        return error("${response.code()}:${response.message()}")
    } catch (e: Exception) {
        return error(e.message ?: e.toString())
    }
}

private fun <T> error(message: String): State<T> {
    Timber.e("RemoteDataSource ERROR!", message)
    return State.Error(Exception("Network call failed: $message"))
}