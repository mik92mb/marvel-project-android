package it.marvel.ui.home.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import it.marvel.network.data.local.LocalDataSource
import it.marvel.network.entities.Character
import it.marvel.network.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class DetailViewModel(private val localDataSource: LocalDataSource) : ViewModel() {

    private val mutableCharacter = SingleLiveEvent<Character>()
    val character: LiveData<Character>
        get() = mutableCharacter

    fun setCharacter(characterSelected: Character) {
        mutableCharacter.value = characterSelected
    }

    fun updateFavorite(isFavorite: Boolean) {
        val safeCharacter = character.value ?: return
        mutableCharacter.value = safeCharacter.copy(isFavorite = !isFavorite)
        viewModelScope.launch {
            localDataSource.updateCharacters(!isFavorite, safeCharacter.id)
        }
    }

    fun getCurrentFavoriteValue() = character.value?.isFavorite ?: false

    fun getComics() = character.value?.comics?.items.orEmpty()
    fun getEvents() = character.value?.events?.items.orEmpty()
    fun getSeries() = character.value?.series?.items.orEmpty()
    fun getStories() = character.value?.stories?.items.orEmpty()
}