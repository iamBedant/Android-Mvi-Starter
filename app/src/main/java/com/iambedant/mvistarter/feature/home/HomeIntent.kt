package com.iambedant.mvistarter.feature.home

import com.iambedant.mvistarter.data.remote.model.ArticlesItem
import com.iambedant.mvistarter.data.remote.model.NewsResponse
import com.iambedant.mvistarter.mvibase.MviIntent


/**
 * Created by @iamBedant on 23/05/18.
 */
sealed class HomeIntent : MviIntent {
    object InitialIntent : HomeIntent()
    data class ClickIntent(val article: ArticlesItem):HomeIntent()
}