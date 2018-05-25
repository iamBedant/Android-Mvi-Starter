package com.iambedant.mvistarter.feature.home

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import javax.inject.Inject

/**
 * Created by @iamBedant on 16/05/18.
 */
data class HomeViewmodelFactory @Inject constructor(private val homeActionProcessorHolder: HomeActionProcessorHolder)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(homeActionProcessorHolder) as T
    }
}