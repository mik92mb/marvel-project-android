package it.marvel.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import it.marvel.network.entities.Character
import it.marvel.network.repository.MarvelRepository
import it.marvel.network.utils.SingleLiveEvent
import it.marvel.network.utils.State
import kotlinx.coroutines.launch

class HomeViewModel(private val marvelRepository: MarvelRepository) : ViewModel() {

    private val mutableState = SingleLiveEvent<State<List<Character>>>()
    val state: LiveData<State<List<Character>>>
        get() = mutableState

    private val mutableCharacterFiltered = SingleLiveEvent<List<Character>>()
    val stateCharacterFiltered: LiveData<List<Character>>
        get() = mutableCharacterFiltered

    fun getCharacters() {
        viewModelScope.launch {
            mutableState.postValue(State.Loading)
            mutableState.postValue(marvelRepository.getCharacters())
        }
    }

    fun setCharactersFiltered(character: List<Character>) =
        mutableCharacterFiltered.postValue(character)

    fun getCharacterFiltered(query: String): List<Character> =
        stateCharacterFiltered.value?.filter { character ->
            (character.name?.contains(query, true) ?: false ||
                    character.description?.contains(query, true) ?: false)
        } ?: emptyList()

}