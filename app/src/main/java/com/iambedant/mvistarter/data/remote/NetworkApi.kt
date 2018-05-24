package com.iambedant.mvistarter.data.remote

import com.iambedant.mvistarter.data.remote.model.LoginRequest
import com.iambedant.mvistarter.data.remote.model.LoginResponse
import io.reactivex.Single
import retrofit2.http.Body
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
}

