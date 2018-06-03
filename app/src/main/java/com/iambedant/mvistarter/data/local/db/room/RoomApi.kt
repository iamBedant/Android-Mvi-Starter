package com.iambedant.mvistarter.data.local.db.room

import android.arch.persistence.room.*
import com.iambedant.mvistarter.data.remote.model.NewsResponse
import io.reactivex.Single


/**
 * Created by @iamBedant on 03/06/18.
 */

@Dao
interface RoomApi {

    @Query("SELECT * FROM news_response")
    fun loadNews(): Single<NewsResponse>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNews(newsResponse: NewsResponse)

    @Delete
    fun delete(): Int

}