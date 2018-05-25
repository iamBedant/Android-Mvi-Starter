package com.iambedant.mvistarter.data.remote.model


import com.google.gson.annotations.SerializedName

data class ArticlesItem(@SerializedName("publishedAt")
                        val publishedAt: String = "",
                        @SerializedName("author")
                        val author: String = "",
                        @SerializedName("urlToImage")
                        val urlToImage: String = "",
                        @SerializedName("description")
                        val description: String = "",
                        @SerializedName("source")
                        val source: Source,
                        @SerializedName("title")
                        val title: String = "",
                        @SerializedName("url")
                        val url: String = "")


data class NewsResponse(@SerializedName("totalResults")
                        val totalResults: Int = 0,
                        @SerializedName("articles")
                        val articles: List<ArticlesItem>,
                        @SerializedName("status")
                        val status: String = "")


data class Source(@SerializedName("name")
                  val name: String = "",
                  @SerializedName("id")
                  val id: String? = null)


