package it.marvel.network.data.remote

import it.marvel.network.entities.CharacterDataWrapper
import it.marvel.utils.Costants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelAPI {

    @GET("/v1/public/characters")
    suspend fun getCharacters(
        @Query("apikey") apiKey: String,
        @Query("ts") timestamp: String,
        @Query("hash") hash: String,
        @Query("limit") limit: Int,
    ): Response<CharacterDataWrapper>
}