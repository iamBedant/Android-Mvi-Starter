package com.iambedant.mvistarter.data.remote.model


import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

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
        @PrimaryKey(autoGenerate = true)
        val id : String,

        @ColumnInfo(name = "name")
        @SerializedName("totalResults")
        val totalResults: Int = 0,


        @SerializedName("articles")
        val articles: List<ArticlesItem>,

        @ColumnInfo(name = "status")
        @SerializedName("status")
        val status: String = "")


data class Source(
        @ColumnInfo(name = "name")
        @SerializedName("name")
        val name: String = "",

        @ColumnInfo(name = "id")
        @SerializedName("id")
        val id: String? = null)


