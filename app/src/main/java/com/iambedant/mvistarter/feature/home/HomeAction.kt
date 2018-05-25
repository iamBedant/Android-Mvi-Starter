package com.iambedant.mvistarter.feature.home

import com.iambedant.mvistarter.data.remote.model.ArticlesItem
import com.iambedant.mvistarter.mvibase.MviAction


/**
 * Created by @iamBedant on 23/05/18.
 */
sealed class HomeAction : MviAction {
    object LoadHomeAction : HomeAction()
    data class ClickAction(val article: ArticlesItem) : HomeAction()
}