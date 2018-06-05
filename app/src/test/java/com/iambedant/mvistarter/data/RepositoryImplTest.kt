package com.iambedant.mvistarter.data

import com.iambedant.mvistarter.data.local.Local
import com.iambedant.mvistarter.data.remote.Network
import com.iambedant.mvistarter.data.remote.model.LoginRequest
import com.iambedant.mvistarter.data.remote.model.LoginResponse
import com.iambedant.mvistarter.data.remote.model.NewsResponse
import com.iambedant.mvistarter.data.remote.model.Response
import com.iambedant.mvistarter.util.schedulers.BaseSchedulerProvider
import com.iambedant.mvistarter.util.schedulers.ImmediateSchedulerProvider
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations



/**
 * Created by @iamBedant on 01/06/18.
 */
class RepositoryImplTest {
    @Mock
    private lateinit var network: Network
    @Mock
    private lateinit var local: Local
    private lateinit var schedulerProvider: BaseSchedulerProvider
    var testObserver = TestObserver<NewsResponse>()

    private lateinit var repository: RepositoryImpl
    @Before
    fun setUpRepository() {
        MockitoAnnotations.initMocks(this)
        schedulerProvider = ImmediateSchedulerProvider()
        repository = RepositoryImpl(network, local,schedulerProvider)
    }

    @Test
    fun loadNewsTest() {
        whenever(network.loadNews()).thenReturn(Single.just(newsResponsefresh))
        whenever(local.loadNews()).thenReturn(Flowable.just(newsResponse))
        repository.loadNews().subscribe(testObserver)
        Mockito.verify<Network>(network).loadNews()
        Mockito.verify<Local>(local).loadNews()
        Mockito.verify<Local>(local).storeNews(newsResponsefresh)
    }

    @Test
    fun doLogin() {
        whenever(network.doLogin(loginRequest)).thenReturn(Single.just(loginResponse))
        repository.doLogin(loginRequest)
        Mockito.verify<Network>(network).doLogin(loginRequest)
    }

    companion object {
        private val newsResponse = NewsResponse(0, 10, listOf(), "")
        private val newsResponsefresh = NewsResponse(0, 12, listOf(), "")
        private val loginResponse = LoginResponse("", Response("", ""))
        private val loginRequest = LoginRequest("", "")
    }
}