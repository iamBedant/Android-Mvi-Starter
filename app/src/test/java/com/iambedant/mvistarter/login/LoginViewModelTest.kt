package com.iambedant.mvistarter.login

import android.arch.lifecycle.Observer
import com.iambedant.mvistarter.data.Repository
import com.iambedant.mvistarter.data.remote.model.LoginResponse
import com.iambedant.mvistarter.feature.login.LoginActionProcessorHolder
import com.iambedant.mvistarter.feature.login.LoginIntent
import com.iambedant.mvistarter.feature.login.LoginViewModel
import com.iambedant.mvistarter.feature.login.LoginViewState
import com.iambedant.mvistarter.util.schedulers.BaseSchedulerProvider
import com.iambedant.mvistarter.util.schedulers.ImmediateSchedulerProvider
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Observable
import org.mockito.Mockito.`when` as whenever
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.iambedant.mvistarter.data.remote.model.Response
import com.nhaarman.mockito_kotlin.any
import io.reactivex.Single
import org.junit.Rule
import org.junit.rules.TestRule


/**
 * Created by @iamBedant on 27/05/18.
 */
class LoginViewModelTest {
    @Mock
    private lateinit var repository: Repository
    private lateinit var schedulerProvider: BaseSchedulerProvider
    private lateinit var loginViewModel: LoginViewModel
    @Mock
    lateinit var observer: Observer<LoginViewState>

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUpLoginViewModel() {
        MockitoAnnotations.initMocks(this)
        schedulerProvider = ImmediateSchedulerProvider()
        loginViewModel = LoginViewModel(LoginActionProcessorHolder(repository, schedulerProvider))
        loginViewModel.states().observeForever(observer)
    }

    @Test
    fun initialIntentTest() {
        loginViewModel.processIntents(Observable.just(LoginIntent.InitialIntent))
        verify(observer).onChanged(LoginViewState(isError = false,
                isLoading = false,
                password = "",
                userId = "",
                isLoginSuccessful = false,
                errorMessage = ""))
    }

    @Test
    fun typePasswordIntentTest() {
        loginViewModel.processIntents(Observable.just(LoginIntent.typePasswordIntent("hi")))
        verify(observer).onChanged(LoginViewState(isError = false,
                isLoading = false,
                password = "hi",
                userId = "",
                isLoginSuccessful = false,
                errorMessage = ""))
    }

    @Test
    fun typeUserIdIntentTest() {
        loginViewModel.processIntents(Observable.just(LoginIntent.typeUserIdIntent("hello")))
        assert(loginViewModel.states().value?.userId == "hello")
    }

    @Test
    fun loginWithValidUsernamePassword() {
        whenever(repository.doLogin(any())).thenReturn(Single.just(LoginResponse("", Response(name = "John"), "")))
        loginViewModel.processIntents(Observable.just(LoginIntent.DoLoginIntent("hello", "Hi")))
        assert(loginViewModel.states().value?.isLoginSuccessful == true)
    }

    @Test
    fun loginWithEmptyUsernamePassword() {
        loginViewModel.processIntents(Observable.just(LoginIntent.DoLoginIntent("", "")))
        verify(observer).onChanged(LoginViewState(isError = true,
                isLoading = false,
                password = "",
                userId = "",
                isLoginSuccessful = false,
                errorMessage = "UserId or password can't be empty"))
    }


    @Test
    fun loginWithValidUsernamePasswordq() {
        whenever(repository.doLogin(any())).thenReturn(Single.just(LoginResponse("", Response(), "")))
        loginViewModel.processIntents(Observable.just(LoginIntent.DoLoginIntent("hello", "Hi")))
        //Loading ViewSstate
        verify(observer).onChanged(LoginViewState(isError = false,
                isLoading = true,
                password = "",
                userId = "",
                isLoginSuccessful = false,
                errorMessage = ""))
        //LoginSuccess Viewstate
        verify(observer).onChanged(LoginViewState(isError = false,
                isLoading = false,
                password = "",
                userId = "",
                isLoginSuccessful = true,
                errorMessage = ""))
    }

    @Test
    fun loginFailTest() {
        whenever(repository.doLogin(any())).thenReturn(Single.error(Throwable("This is some kind of error")))
        loginViewModel.processIntents(Observable.just(LoginIntent.DoLoginIntent("hello", "Hi")))
        verify(observer).onChanged(LoginViewState(isError = false,
                isLoading = true,
                password = "",
                userId = "",
                isLoginSuccessful = false,
                errorMessage = ""))
        verify(observer).onChanged(LoginViewState(isLoading = false,
                userId = "",
                password = "", errorMessage = "This is some kind of error",
                isError = true,
                isLoginSuccessful = false))


    }


}
