package com.iambedant.mvistarter.data.local

import com.iambedant.mvistarter.data.local.db.room.RoomApi
import com.iambedant.mvistarter.data.remote.model.NewsResponse
import io.reactivex.Single

/**
 * Created by @iamBedant on 01/06/18.
 */
class LocalImpl(private val roomApi: RoomApi) : Local {

    override fun storeNews(newsResponse: NewsResponse) {
        roomApi.insertNews(newsResponse)
    }

    override fun loadNews(): Single<NewsResponse> {
        return roomApi.loadNews()
    }
}