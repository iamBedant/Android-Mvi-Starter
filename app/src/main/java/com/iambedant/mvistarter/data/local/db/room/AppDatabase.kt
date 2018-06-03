package com.iambedant.mvistarter.data.local.db.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.iambedant.mvistarter.data.remote.model.NewsResponse

/**
 * Created by @iamBedant on 03/06/18.
 */

@Database(entities = [(NewsResponse::class)], version = 1,  exportSchema = false)
@TypeConverters(DataTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getDao(): RoomApi
}