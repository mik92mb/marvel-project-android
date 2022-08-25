package it.marvel.network.data.remote

import it.marvel.network.entities.CharacterDataWrapper
import it.marvel.utils.Costants
import it.marvel.network.utils.Support
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelAPI {

    @GET("/v1/public/characters")
    suspend fun getCharacters(
        @Query("apikey") apiKey: String = Costants.PUBLIC_KEY,
        @Query("ts") ts: String = Support.getTimeStamp(),
        @Query("hash") hash: String = Support.hashGenerator(),
        @Query("limit") limit: Int = 100,
    ): Response<CharacterDataWrapper>
}