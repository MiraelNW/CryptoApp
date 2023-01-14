package com.example.cryptoapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider
import kotlin.reflect.KClass

class CoinViewModelFactory @Inject constructor(
    private val viewModelProviders : @JvmSuppressWildcards Map<Class<out ViewModel>, Provider<ViewModel>>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return viewModelProviders[modelClass]?.get() as T
    }

}