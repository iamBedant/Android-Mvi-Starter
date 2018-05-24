package com.iambedant.mvistarter.data

import com.iambedant.mvistarter.data.remote.NetworkService
import com.iambedant.mvistarter.data.remote.model.LoginRequest
import com.iambedant.mvistarter.data.remote.model.LoginResponse
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by @iamBedant on 23/05/18.
 */
class RepositoryImpl @Inject constructor(private val networkService:NetworkService) : Repository{
    override fun doLogin(loginRequest: LoginRequest): Single<LoginResponse> {
        return networkService.doLogin(loginRequest)
    }
}