package com.iambedant.mvistarter.data.remote.model


import android.arch.persistence.room.*
import com.google.gson.annotations.SerializedName
import com.iambedant.mvistarter.data.local.db.room.DataTypeConverter

data class ArticlesItem(
        @ColumnInfo(name = "publishedAt")
        @SerializedName("publishedAt")
        val publishedAt: String = "",

        @ColumnInfo(name = "author")
        @SerializedName("author")
        val author: String = "",

        @ColumnInfo(name = "urlToImage")
        @SerializedName("urlToImage")
        val urlToImage: String = "",

        @ColumnInfo(name = "description")
        @SerializedName("description")
        val description: String = "",

        @Embedded
        @SerializedName("source")
        val source: Source,

        @ColumnInfo(name = "title")
        @SerializedName("title")
        val title: String = "",

        @ColumnInfo(name = "url")
        @SerializedName("url")
        val url: String = "")


@Entity(tableName = "news_response")
data class NewsResponse(
        @PrimaryKey
        val id: Int,

        @ColumnInfo(name = "name")
        @SerializedName("totalResults")
        val totalResults: Int = 0,

        @ColumnInfo(name = "articles")
        @TypeConverters(DataTypeConverter::class)
        @SerializedName("articles")
        val articles: List<ArticlesItem> = emptyList(),

        @ColumnInfo(name = "status")
        @SerializedName("status")
        val status: String = "")


data class Source(
        @ColumnInfo(name = "name")
        @SerializedName("name")
        val name: String = "",

        @ColumnInfo(name = "id")
        @SerializedName("id")
        val id: String = "")


