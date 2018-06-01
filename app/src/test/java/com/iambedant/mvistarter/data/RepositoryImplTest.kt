package com.iambedant.mvistarter.data

import com.iambedant.mvistarter.data.remote.Network
import com.iambedant.mvistarter.data.remote.model.LoginRequest
import com.iambedant.mvistarter.data.remote.model.LoginResponse
import com.iambedant.mvistarter.data.remote.model.NewsResponse
import com.iambedant.mvistarter.data.remote.model.Response
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
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
    private lateinit var repository: RepositoryImpl
    @Before
    fun setUpRepository(){
        MockitoAnnotations.initMocks(this)
        repository = RepositoryImpl(network)
    }

    @Test
    fun loadNewsTest(){
        whenever(network.loadNews()).thenReturn(Single.just(newsResponse))
        repository.loadNews()
        Mockito.verify<Network>(network).loadNews()
    }

    @Test
    fun doLogin(){
        whenever(network.doLogin(loginRequest)).thenReturn(Single.just(loginResponse))
        repository.doLogin(loginRequest)
        Mockito.verify<Network>(network).doLogin(loginRequest)
    }

    companion object {
        private val newsResponse = NewsResponse(0, listOf(),"")
        private val loginResponse = LoginResponse("", Response("",""))
        private val loginRequest = LoginRequest("","")
    }
}