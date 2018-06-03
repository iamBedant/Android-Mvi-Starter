package com.iambedant.mvistarter.data

import com.iambedant.mvistarter.data.local.Local
import com.iambedant.mvistarter.data.remote.Network
import com.iambedant.mvistarter.data.remote.model.LoginRequest
import com.iambedant.mvistarter.data.remote.model.LoginResponse
import com.iambedant.mvistarter.data.remote.model.NewsResponse
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by @iamBedant on 23/05/18.
 */
class RepositoryImpl @Inject constructor(private val network: Network, private val local: Local) : Repository {

    override fun loadNews(): Observable<NewsResponse> {
        return Observable.mergeDelayError(
                local.loadNews().subscribeOn(Schedulers.io()).toObservable(),
                network.loadNews().doOnSuccess { response ->
                    local.storeNews(response)
                }.subscribeOn(Schedulers.io()).toObservable()
        )
    }

    override fun doLogin(loginRequest: LoginRequest): Single<LoginResponse> {
        return network.doLogin(loginRequest)
    }
}