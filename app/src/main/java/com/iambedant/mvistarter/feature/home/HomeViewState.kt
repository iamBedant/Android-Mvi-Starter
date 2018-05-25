package com.iambedant.mvistarter.feature.home

import com.iambedant.mvistarter.data.remote.model.ArticlesItem
import com.iambedant.mvistarter.mvibase.MviViewState


/**
 * Created by @iamBedant on 23/05/18.
 */
data class HomeViewState(val isLoading: Boolean,
                         val errorMessage: String,
                         val isError: Boolean,
                         val articles: List<ArticlesItem>,
                         val showShareOption: Boolean,
                         val shareArticle: ArticlesItem?
) : MviViewState {


    companion object {
        fun idle(): HomeViewState {
            return HomeViewState(
                    isLoading = false,
                    isError = false,
                    errorMessage = "",
                    articles = emptyList(),
                    showShareOption = false,
                    shareArticle = null

            )
        }
    }
}