package com.iambedant.mvistarter

import android.support.test.runner.AndroidJUnit4
import com.iambedant.mvistarter.data.local.db.room.AppDatabase
import org.junit.Before
import org.junit.runner.RunWith
import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import org.junit.After
import org.junit.Test
import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.iambedant.mvistarter.data.remote.model.ArticlesItem
import com.iambedant.mvistarter.data.remote.model.NewsResponse
import com.iambedant.mvistarter.data.remote.model.Source
import org.junit.Rule


/**
 * Created by @iamBedant on 11/07/18.
 */

@RunWith(AndroidJUnit4::class)
class RoomTest {

    private lateinit var database: AppDatabase
    @JvmField @Rule var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val newsResponse = createNewsResponse()

    @Before
    fun initDb() {
        database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getTargetContext(), AppDatabase::class.java)
                .allowMainThreadQueries()
                .build()
    }

    @After fun closeDb() {
        database.close()
    }

    @Test fun emptyDatabaseTest(){
        database.getDao().loadNews().test().assertNoValues()
    }

    @Test
    fun storeNewsTest() {
        database.getDao().insertNews(newsResponse)
        database.getDao().loadNews()
                .test()
                .assertValue{it == newsResponse}
    }

    private fun createNewsResponse(): NewsResponse{
        val articleList = mutableListOf<ArticlesItem>()
        for (i in 1..10){
            articleList.add(ArticlesItem("$i","$i","$i","$i", Source("$i","$i"),"$i","$i"))
        }
        return NewsResponse(0,articleList.size,articleList)

    }
}