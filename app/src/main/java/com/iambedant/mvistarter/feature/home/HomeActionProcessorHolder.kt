package com.iambedant.mvistarter.feature.home

import com.iambedant.mvistarter.data.Repository
import com.iambedant.mvistarter.util.schedulers.BaseSchedulerProvider
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import javax.inject.Inject

/**
 * Created by @iamBedant on 23/05/18.
 */
class HomeActionProcessorHolder @Inject constructor(private val repository: Repository,
                                                    private val schedulerProvider: BaseSchedulerProvider) {
    fun transformAction(): ObservableTransformer<HomeAction, HomeResult> {
        return ObservableTransformer { action ->
            action.publish { shared ->
                Observable.merge(
                        shared.ofType(HomeAction.LoadHomeAction::class.java).compose(loadHome()),
                        shared.ofType(HomeAction.ClickAction::class.java).compose(shareArticle())

                )
            }
        }

    }

    private fun shareArticle(): ObservableTransformer<HomeAction.ClickAction, HomeResult.ClickResult> {
        return ObservableTransformer { action ->
            action.flatMap {
                Observable.just(HomeResult.ClickResult(it.article))
            }
        }

    }

    private fun loadHome(): ObservableTransformer<HomeAction.LoadHomeAction, HomeResult.LoadHomeResult> {
        return ObservableTransformer { action ->
            action.flatMap {
                repository.loadNews()
                        .toObservable()
                        .map { response -> HomeResult.LoadHomeResult.Success(response.articles) }
                        .cast(HomeResult.LoadHomeResult::class.java)
                        .onErrorReturn { t ->
                            HomeResult.LoadHomeResult.Failure(t.localizedMessage)
                        }
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .startWith(HomeResult.LoadHomeResult.InFlight)
            }
        }

    }


}