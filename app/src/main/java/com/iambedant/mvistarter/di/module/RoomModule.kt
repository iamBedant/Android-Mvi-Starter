package com.iambedant.mvistarter.di.module

import android.app.Application
import android.arch.persistence.room.Room
import com.iambedant.mvistarter.data.local.Local
import com.iambedant.mvistarter.data.local.LocalImpl
import com.iambedant.mvistarter.data.local.db.room.AppDatabase
import com.iambedant.mvistarter.data.local.db.room.RoomApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


/**
 * Created by @iamBedant on 03/06/18.
 */
@Module
class RoomModule(val application: Application) {

    private lateinit var demoDatabase: AppDatabase

    fun RoomModule(mApplication: Application) {
        demoDatabase = Room.databaseBuilder(mApplication, AppDatabase::class.java, "news-db").build()
    }

    @Singleton
    @Provides
    fun providesRoomDatabase(): AppDatabase = demoDatabase


    @Singleton
    @Provides
    fun providesDao(demoDatabase: AppDatabase): RoomApi = demoDatabase.getDao()


    @Singleton
    @Provides
    fun provideLocalImpl(roomApi: RoomApi): Local = LocalImpl(roomApi)

}