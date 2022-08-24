package it.marvel.network.utils

sealed class State<out T> {
    object Loading : State<Nothing>()
    data class Success<out T>(val data: T) : State<T>()
    data class Error(val exception: Exception) : State<Nothing>()
}