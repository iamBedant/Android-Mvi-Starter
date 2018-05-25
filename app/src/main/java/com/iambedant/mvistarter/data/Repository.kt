package com.iambedant.mvistarter.data

import com.iambedant.mvistarter.data.remote.model.LoginRequest
import com.iambedant.mvistarter.data.remote.model.LoginResponse
import com.iambedant.mvistarter.data.remote.model.NewsResponse
import io.reactivex.Single

/**
 * Created by @iamBedant on 23/05/18.
 */
interface Repository {
    fun doLogin(loginRequest: LoginRequest): Single<LoginResponse>
    fun loadNews(): Single<NewsResponse>
}