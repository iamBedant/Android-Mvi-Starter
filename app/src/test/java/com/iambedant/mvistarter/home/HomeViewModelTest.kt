package com.iambedant.mvistarter.home

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.iambedant.mvistarter.data.Repository
import com.iambedant.mvistarter.data.remote.model.ArticlesItem
import com.iambedant.mvistarter.data.remote.model.NewsResponse
import com.iambedant.mvistarter.data.remote.model.Source
import com.iambedant.mvistarter.feature.home.HomeActionProcessorHolder
import com.iambedant.mvistarter.feature.home.HomeIntent
import com.iambedant.mvistarter.feature.home.HomeViewModel
import com.iambedant.mvistarter.feature.home.HomeViewState
import com.iambedant.mvistarter.util.schedulers.BaseSchedulerProvider
import com.iambedant.mvistarter.util.schedulers.ImmediateSchedulerProvider
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.MockitoAnnotations

/**
 * Created by @iamBedant on 01/06/18.
 */
class HomeViewModelTest {
    @Mock
    private lateinit var repository: Repository
    private lateinit var schedulerProvider: BaseSchedulerProvider
    private lateinit var homeViewModel: HomeViewModel
    @Mock
    lateinit var observer: Observer<HomeViewState>

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUpLoginViewModel() {
        MockitoAnnotations.initMocks(this)
        schedulerProvider = ImmediateSchedulerProvider()
        homeViewModel = HomeViewModel(HomeActionProcessorHolder(repository, schedulerProvider))
        homeViewModel.states().observeForever(observer)
    }

    @Test
    fun InitialIntentTest() {
        val articles = listOf<ArticlesItem>()
        whenever(repository.loadNews()).thenReturn(Observable.just(NewsResponse(
                id = 0,
                totalResults = 10,
                articles = articles,
                status = ""
        )))
        homeViewModel.processIntents(Observable.just(HomeIntent.InitialIntent))
        verify(observer).onChanged(HomeViewState(
                isLoading = true,
                isError = false,
                errorMessage = "",
                articles = emptyList(),
                showShareOption = false,
                shareArticle = null
        ))
        //TODO: figure out why this viewstate in invoked twice
        verify(observer, times(2)).onChanged(HomeViewState(
                isLoading = false,
                isError = false,
                errorMessage = "",
                articles = articles,
                shareArticle = null,
                showShareOption = false
        ))
    }

    @Test
    fun LoadErrorTest() {
        whenever(repository.loadNews()).thenReturn(Observable.error(Throwable("This is somekind of error")))
        homeViewModel.processIntents(Observable.just(HomeIntent.InitialIntent))
        verify(observer).onChanged(HomeViewState(
                isLoading = true,
                isError = false,
                errorMessage = "",
                articles = emptyList(),
                showShareOption = false,
                shareArticle = null
        ))
        verify(observer).onChanged(HomeViewState(
                isLoading = false,
                isError = true,
                errorMessage = "This is somekind of error",
                articles = emptyList(),
                shareArticle = null,
                showShareOption = false
        ))
    }

    @Test
    fun onNewsClickTest() {
        val article = ArticlesItem("", "", ",", "", Source("", ""), "", "")
        homeViewModel.processIntents(Observable.just(HomeIntent.ClickIntent(article)))
        verify(observer).onChanged(HomeViewState(
                isLoading = false,
                isError = false,
                errorMessage = "",
                articles = emptyList(),
                shareArticle = article,
                showShareOption = true
        ))
    }

}