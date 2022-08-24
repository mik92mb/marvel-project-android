package it.marvel.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import it.marvel.network.repository.MarvelRepository
import it.marvel.network.utils.SingleLiveEvent
import it.marvel.network.utils.State
import it.marvel.network.entities.Character
import kotlinx.coroutines.launch

class HomeViewModel(private val marvelRepository: MarvelRepository) : ViewModel() {

    private val mutableState = SingleLiveEvent<State<List<Character>>>()
    val state: LiveData<State<List<Character>>>
        get() = mutableState

    fun getCharacters() {
        viewModelScope.launch {
            mutableState.postValue(State.Loading)
            mutableState.postValue(marvelRepository.getCharacters())
        }
    }
}