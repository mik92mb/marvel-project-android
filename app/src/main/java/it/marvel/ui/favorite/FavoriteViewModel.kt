package it.marvel.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import it.marvel.network.data.local.LocalDataSource
import it.marvel.network.entities.Character
import it.marvel.network.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class FavoriteViewModel(private val localDataSource: LocalDataSource) : ViewModel() {

    private val mutableCharacter = SingleLiveEvent<List<Character>>()
    val state: LiveData<List<Character>>
        get() = mutableCharacter

    fun getFavoriteCharacters() {
        viewModelScope.launch {
            mutableCharacter.postValue(localDataSource.getFavoriteCharacters())
        }
    }
}