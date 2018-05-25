package com.iambedant.mvistarter.data.remote

import com.iambedant.mvistarter.data.remote.model.LoginRequest
import com.iambedant.mvistarter.data.remote.model.LoginResponse
import com.iambedant.mvistarter.data.remote.model.NewsResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Created by @iamBedant on 23/05/18.
 */
interface NetworkApi {
    companion object {
        const val BASE_URL = "http://demo1570114.mockable.io/"
    }

    @POST("auth/login/")
    fun doLogin (@Body request: LoginRequest): Single<LoginResponse>

    @GET("https://newsapi.org/v2/top-headlines?country=us&apiKey=d33e891e97b74a838f165a171a07abda")
    fun loadNews(): Single<NewsResponse>
}

