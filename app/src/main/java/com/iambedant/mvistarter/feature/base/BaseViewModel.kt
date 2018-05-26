package com.iambedant.mvistarter.feature.base

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.iambedant.mvistarter.mvibase.*
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject

/**
 * Created by @iamBedant on 26/05/18.
 */
abstract class BaseViewModel<I: MviIntent, S: MviViewState, A:MviAction, R: MviResult> : ViewModel(), MviViewModel<I, S> {

    private val disposables: CompositeDisposable = CompositeDisposable()
    private val intentsSubject: PublishSubject<I> = PublishSubject.create()
    private val statesLiveData: MutableLiveData<S> = MutableLiveData()
    lateinit var   statesObservable: Observable<S>
    abstract fun intentFilter(): ObservableTransformer<I, I>

    override fun processIntents(intents: Observable<I>) {
        intents.subscribe(intentsSubject)
    }

    override fun states(): LiveData<S> {
        return statesLiveData
    }

    fun connectObservableToLiveData(){
        statesObservable = compose()
        disposables.add(statesObservable.subscribe{statesLiveData.postValue(it)})
    }

    abstract fun actionFromIntent(intent: I):A


    fun compose(): Observable<S>{
        return intentsSubject
                .compose(intentFilter())
                .map(this::actionFromIntent)
                .compose(actionProcessorHolder().transformFromAction())
                .scan(initialState(), reducer())
                .distinctUntilChanged()
                .replay(1)
                .autoConnect(0)
    }

    abstract fun actionProcessorHolder():MviActionProcessorHolder<A,R>

    abstract fun reducer(): BiFunction<S, R, S>

    abstract fun initialState() : S
}