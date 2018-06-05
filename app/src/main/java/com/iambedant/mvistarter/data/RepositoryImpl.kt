package com.iambedant.mvistarter.data

import com.iambedant.mvistarter.data.local.Local
import com.iambedant.mvistarter.data.remote.Network
import com.iambedant.mvistarter.data.remote.model.LoginRequest
import com.iambedant.mvistarter.data.remote.model.LoginResponse
import com.iambedant.mvistarter.data.remote.model.NewsResponse
import com.iambedant.mvistarter.util.schedulers.BaseSchedulerProvider
import io.reactivex.Observable
import io.reactivex.Single
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by @iamBedant on 23/05/18.
 */
class RepositoryImpl @Inject constructor(private val network: Network, private val local: Local, private val schedulerProvider: BaseSchedulerProvider) : Repository {

    override fun loadNews(): Observable<NewsResponse> {
        return Observable.mergeDelayError(
                local.loadNews().toObservable(),
                network.loadNews().doOnSuccess { response ->
                    local.storeNews(response)
                }.doOnError { t->
                    Timber.d(t.localizedMessage)
                }.toObservable()
        )
    }

    override fun doLogin(loginRequest: LoginRequest): Single<LoginResponse> {
        return network.doLogin(loginRequest)
    }
}