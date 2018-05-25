package com.iambedant.mvistarter.feature.home

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.iambedant.mvistarter.mvibase.MviViewModel
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject

/**
 * Created by @iamBedant on 23/05/18.
 */
class HomeViewModel(private val homeActionProcessorHolder:
                    HomeActionProcessorHolder) :
        ViewModel(),
        MviViewModel<HomeIntent, HomeViewState> {

    private val stateLiveData: MutableLiveData<HomeViewState> = MutableLiveData()
    private val intentSubject: PublishSubject<HomeIntent> = PublishSubject.create()
    private val disposables: CompositeDisposable = CompositeDisposable()
    private val statesObservable: Observable<HomeViewState> = compose()
    private val intentFilter : ObservableTransformer<HomeIntent,HomeIntent>
    get() = ObservableTransformer { intents->
        intents.publish {shared->
            Observable.merge<HomeIntent>(
                    shared.ofType(HomeIntent.InitialIntent::class.java).take(1),
                    shared.filter { it!=HomeIntent.InitialIntent }
            )
        }
    }


    init {
        disposables.add(statesObservable.subscribe{stateLiveData.postValue(it)})
    }
    override fun states(): LiveData<HomeViewState> {
        return stateLiveData
    }

    override fun processIntents(intents: Observable<HomeIntent>) {
        intents.subscribe(intentSubject)
    }

    private fun compose():Observable<HomeViewState>{
        return intentSubject
                .compose(intentFilter)
                .map(this::actionFromIntent)
                .compose(homeActionProcessorHolder.transformAction())
                .scan(HomeViewState.idle(), reducer)
                .distinctUntilChanged()
                .replay(1)
                .autoConnect(0)
    }

    private fun actionFromIntent(intent: HomeIntent):HomeAction{
        return when (intent){
            is HomeIntent.InitialIntent -> HomeAction.LoadHomeAction
            is HomeIntent.ClickIntent -> HomeAction.ClickAction(intent.article)
        }
    }

    companion object {
        private val reducer = BiFunction{ previousState : HomeViewState, result: HomeResult->
            when(result){
                is HomeResult.LoadHomeResult->{
                    when(result){
                        is HomeResult.LoadHomeResult.Success->{
                            previousState.copy(isLoading = false,isError = false, errorMessage = "",articles = result.newsList)
                        }
                        is HomeResult.LoadHomeResult.Failure->{
                            previousState.copy(isLoading = false, isError = true, errorMessage = result.errorMessage)
                        }
                        is HomeResult.LoadHomeResult.InFlight->{
                            previousState.copy(isLoading = true, isError = false, errorMessage = "", showShareOption = false)
                        }
                    }
                }

                is HomeResult.ClickResult->{
                    //TODO: if user clicks twice in the same item share intent will not show for 2nd time coz distinctUntilChanged()
                    //TODO: Figure out a way to avoid this.
                    previousState.copy(showShareOption = true, shareArticle = result.article)
                }
            }
        }
    }

}