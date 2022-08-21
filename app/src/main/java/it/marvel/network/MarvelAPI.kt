package it.marvel.network

import it.marvel.Costants
import it.marvel.getTimeStamp
import it.marvel.hashGenerator
import it.marvel.model.CharacterDataWrapper
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelAPI {

    @GET("/v1/public/characters")
    suspend fun getCharacters(
        @Query("apikey") apiKey: String = Costants.PUBLIC_KEY,
        @Query("ts") ts: String = getTimeStamp(),
        @Query("hash") hash: String = hashGenerator(),
        @Query("limit") limit: Int = 100,
    ): CharacterDataWrapper
}