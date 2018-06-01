package com.iambedant.mvistarter.data.remote

import com.iambedant.mvistarter.data.remote.model.LoginRequest
import com.iambedant.mvistarter.data.remote.model.NewsResponse
import com.iambedant.mvistarter.data.remote.retrofit.NetworkApi
import io.reactivex.Single

/**
 * Created by @iamBedant on 01/06/18.
 */
class NetworkImpl(private val networkApi: NetworkApi) : Network {
    override fun doLogin(loginRequest: LoginRequest) = networkApi.doLogin(loginRequest)
    override fun loadNews(): Single<NewsResponse> = networkApi.loadNews()
}