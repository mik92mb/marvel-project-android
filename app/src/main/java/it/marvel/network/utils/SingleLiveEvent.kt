package it.marvel.network.utils
/*
 *  Copyright 2017 Google Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import timber.log.Timber
import java.util.concurrent.atomic.AtomicBoolean

class SingleLiveEvent<T> : MutableLiveData<T>() {
    private val isPending = AtomicBoolean(false)

    private fun dispatchToObserver(observer: Observer<in T>, value: T?) {
        if (isPending.compareAndSet(true, false)) {
            observer.onChanged(value)
        }
    }

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        if (hasActiveObservers()) {
            Timber.w("Multiple observers registered but only one will be notified of changes.")
        }

        super.observe(
            owner
        ) { value -> dispatchToObserver(observer, value) }
    }

    @MainThread
    override fun observeForever(observer: Observer<in T>) {
        super.observeForever { value ->
            dispatchToObserver(observer, value)
        }
    }

    @MainThread
    override fun setValue(value: T?) {
        isPending.set(true)
        super.setValue(value)
    }

    @MainThread
    override fun postValue(value: T?) {
        isPending.set(true)
        super.postValue(value)
    }

    @MainThread
    fun call() {
        value = null
    }
}
