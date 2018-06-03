package com.iambedant.mvistarter.data.local.db.room

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.iambedant.mvistarter.data.remote.model.ArticlesItem
import java.util.*


/**
 * Created by @iamBedant on 03/06/18.
 */
class DataTypeConverter {

    private val gson = Gson()

    @TypeConverter
    fun stringToList(data: String?): List<ArticlesItem> {
        if (data == null) {
            return Collections.emptyList()
        }

        val listType = object : TypeToken<List<ArticlesItem>>() {

        }.type

        return gson.fromJson<List<ArticlesItem>>(data, listType)
    }

    @TypeConverter
    fun ListToString(someObjects: List<ArticlesItem>): String {
        return gson.toJson(someObjects)
    }
}