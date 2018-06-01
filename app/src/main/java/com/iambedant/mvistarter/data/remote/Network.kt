package com.iambedant.mvistarter.data.remote

import com.iambedant.mvistarter.data.remote.model.LoginRequest
import com.iambedant.mvistarter.data.remote.model.LoginResponse
import com.iambedant.mvistarter.data.remote.model.NewsResponse
import io.reactivex.Single

/**
 * Created by @iamBedant on 01/06/18.
 */
interface Network {
    fun doLogin(loginRequest: LoginRequest): Single<LoginResponse>
    fun loadNews(): Single<NewsResponse>
}