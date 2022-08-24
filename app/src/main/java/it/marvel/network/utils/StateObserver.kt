package it.marvel.network.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import timber.log.Timber

interface StateObserver {

    val lifecycleOwner: LifecycleOwner

    fun <T> LiveData<State<T>>.observe(
        loading: () -> Unit,
        success: (T) -> Unit,
        error: (Throwable) -> Unit
    ) = observe(lifecycleOwner) {
        when (it) {

            is State.Loading -> {
                Timber.i("SONO IN ON LOADING")
                loading()
            }

            is State.Success<T> -> {
                Timber.i("SONO IN ON SUCCESS")
                success(it.data)
            }

            is State.Error -> {
                Timber.e("SONO IN ON ERROR - $it")
                error(it.exception)
            }
        }
    }
}