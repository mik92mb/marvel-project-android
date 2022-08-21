package it.marvel.network

class MarvelRepository(private val marvelAPI: MarvelAPI) {

    suspend fun getCharacters() = marvelAPI.getCharacters()

}