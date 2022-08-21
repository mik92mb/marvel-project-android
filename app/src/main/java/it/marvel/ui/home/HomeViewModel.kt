package it.marvel.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import it.marvel.App
import it.marvel.model.CharacterDataWrapper
import it.marvel.network.SingleLiveEvent
import it.marvel.network.State
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val mutableState = SingleLiveEvent<State<CharacterDataWrapper>>()
    val state: LiveData<State<CharacterDataWrapper>>
        get() = mutableState

    fun getCharacters() {
        viewModelScope.launch {
            mutableState.postValue(State.Loading)
            try {
                val data = App.marvelRepository.getCharacters()
                mutableState.postValue(State.Success(data))

            } catch (exception: Exception) {
                mutableState.postValue(State.Error(exception))
            }
        }
    }

}