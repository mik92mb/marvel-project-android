package it.marvel.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import it.marvel.network.data.local.LocalDataSource
import it.marvel.network.entities.Character

class FavoriteViewModel(localDataSource: LocalDataSource) : ViewModel() {

    val favoriteCharacters: LiveData<List<Character>> =
        localDataSource.getFavoriteCharacters()

}