package com.iambedant.mvistarter.data

import com.iambedant.mvistarter.data.remote.Network
import com.iambedant.mvistarter.data.remote.model.LoginRequest
import com.iambedant.mvistarter.data.remote.model.LoginResponse
import com.iambedant.mvistarter.data.remote.model.NewsResponse
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by @iamBedant on 23/05/18.
 */
class RepositoryImpl @Inject constructor(private val network: Network) : Repository{
    override fun loadNews(): Single<NewsResponse> {
        return network.loadNews()
    }
    override fun doLogin(loginRequest: LoginRequest): Single<LoginResponse> {
        return network.doLogin(loginRequest)
    }
}