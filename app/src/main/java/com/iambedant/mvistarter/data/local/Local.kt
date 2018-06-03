package com.iambedant.mvistarter.data.local

import com.iambedant.mvistarter.data.remote.model.NewsResponse
import io.reactivex.Single

/**
 * Created by @iamBedant on 01/06/18.
 */
interface Local {
    fun loadNews(): Single<NewsResponse>
    fun storeNews(newsResponse: NewsResponse)
}