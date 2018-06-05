package com.iambedant.mvistarter.data.local.db.room

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.iambedant.mvistarter.data.remote.model.NewsResponse
import io.reactivex.Flowable


/**
 * Created by @iamBedant on 03/06/18.
 */

@Dao
interface RoomApi {

    @Query("SELECT * FROM news_response WHERE id = 0")
    fun loadNews(): Flowable<NewsResponse>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNews(newsResponse: NewsResponse)
}