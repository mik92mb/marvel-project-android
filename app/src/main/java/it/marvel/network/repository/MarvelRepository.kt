package it.marvel.network.repository

import it.marvel.network.utils.State
import it.marvel.network.data.local.LocalDataSource
import it.marvel.network.data.remote.RemoteDataSource
import it.marvel.network.entities.Character
import timber.log.Timber

class MarvelRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) {

    suspend fun getCharacters(): State<List<Character>> {
        val localData = localDataSource.getCharacters()
        Timber.i("SONO IN db: ${localData.size}")
        return if (localData.isEmpty()) {
            getStateFromNetwork()
        } else {
            State.Success(localData)
        }
    }

    private suspend fun getStateFromNetwork(): State<List<Character>> {
        return when (val networkData = remoteDataSource.getCharacters()) {
            is State.Loading -> State.Loading
            is State.Error -> State.Error(networkData.exception)
            is State.Success -> {
                val characters = networkData.data.data?.results.orEmpty()
                localDataSource.insertAllCharacters(characters)
                State.Success(characters)
            }
        }
    }
}