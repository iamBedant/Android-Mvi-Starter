package com.iambedant.mvistarter.data.remote

import com.iambedant.mvistarter.data.remote.model.LoginRequest
import kotlin.math.log

/**
 * Created by @iamBedant on 23/05/18.
 */
class NetworkService(private val networkApi: NetworkApi) {
    fun doLogin(loginRequest: LoginRequest) = networkApi.doLogin(loginRequest)
    fun loadNews() = networkApi.loadNews()
}