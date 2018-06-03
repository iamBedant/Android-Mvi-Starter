package com.iambedant.mvistarter.di.module

import android.arch.persistence.room.Room
import android.content.Context
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
class RoomModule{

    @Singleton
    @Provides
    fun provideRoomDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "news-db").build()
    }

    @Singleton
    @Provides
    fun providesDao(database: AppDatabase): RoomApi = database.getDao()


    @Singleton
    @Provides
    fun provideLocalImpl(roomApi: RoomApi): Local = LocalImpl(roomApi)

}