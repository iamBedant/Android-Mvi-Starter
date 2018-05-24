package com.iambedant.mvistarter.feature.login

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import javax.inject.Inject

/**
 * Created by @iamBedant on 16/05/18.
 */
data class LoginViewmodelFactory @Inject constructor(private val loginActionProcessorHolder: LoginActionProcessorHolder)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginViewModel(loginActionProcessorHolder) as T
    }
}