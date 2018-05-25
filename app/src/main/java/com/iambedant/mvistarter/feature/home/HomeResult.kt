package com.iambedant.mvistarter.feature.home

import com.iambedant.mvistarter.data.remote.model.ArticlesItem
import com.iambedant.mvistarter.mvibase.MviResult


/**
 * Created by @iamBedant on 23/05/18.
 */
sealed class HomeResult : MviResult {
    sealed class LoadHomeResult : HomeResult() {
        data class Success(val newsList: List<ArticlesItem>) : LoadHomeResult()
        data class Failure(val errorMessage: String) : LoadHomeResult()
        object InFlight : LoadHomeResult()
    }

    data class ClickResult(val article: ArticlesItem) : HomeResult()
}