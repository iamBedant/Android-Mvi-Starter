package com.iambedant.mvistarter.feature.login

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.iambedant.pizzaapp.mvibase.MviViewModel
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject

/**
 * Created by @iamBedant on 23/05/18.
 */
class LoginViewModel(private val loginActionProcessorHolder: LoginActionProcessorHolder) :
        ViewModel(),
        MviViewModel<LoginIntent, LoginViewState> {

    private val intentsSubject: PublishSubject<LoginIntent> = PublishSubject.create()
    private val statesObservable: Observable<LoginViewState> = compose()
    private val statesLiveData: MutableLiveData<LoginViewState> = MutableLiveData()
    private val disposables: CompositeDisposable = CompositeDisposable()
    private val intentFilter: ObservableTransformer<LoginIntent, LoginIntent>
        get() = ObservableTransformer { intents ->
            intents.publish { shared ->
                Observable.merge<LoginIntent>(
                        shared.ofType(LoginIntent.InitialIntent::class.java).take(1),
                        shared.filter { it != LoginIntent.InitialIntent }
                )
            }
        }

    init {
        disposables.add(statesObservable.subscribe { statesLiveData.postValue(it)})
    }

    override fun processIntents(intents: Observable<LoginIntent>) {
        intents.subscribe(intentsSubject)
    }

    override fun states(): LiveData<LoginViewState> {
        return statesLiveData
    }

    private fun compose(): Observable<LoginViewState> {
        return intentsSubject
                .compose(intentFilter)
                .map(this::actionFromIntent)
                .compose(loginActionProcessorHolder.transformFromAction())
                .scan(LoginViewState.idle(), reducer)
                .distinctUntilChanged()
                .replay(1)
                .autoConnect(0)
    }

    private fun actionFromIntent(intent: LoginIntent): LoginAction {
        return when (intent) {
            is LoginIntent.InitialIntent -> LoginAction.LoadLoginAction
            is LoginIntent.DoLoginIntent -> LoginAction.DoLoginAction(intent.userId, intent.password)
            is LoginIntent.typePasswordIntent-> LoginAction.TypePasswordAction(intent.password)
            is LoginIntent.typeUserIdIntent -> LoginAction.TypeUserIdAction(intent.userId)
            else -> throw IllegalArgumentException("unknown intent")
        }
    }

    companion object {
        private val reducer = BiFunction { previousState: LoginViewState, result: LoginResult ->
            when (result) {
                is LoginResult.LoadLoginResult -> {
                    when (result) {
                        is LoginResult.LoadLoginResult.Success -> {
                            previousState.copy()
                        }
                    }
                }
                is LoginResult.DoLoginResult -> {
                    when (result) {
                        is LoginResult.DoLoginResult.Success -> {
                            previousState.copy(isLoginSuccessful = true, isLoading = false)
                        }
                        is LoginResult.DoLoginResult.Failure -> {
                            previousState.copy(isLoginSuccessful = false, isError = true, errorMessage = result.errorMessage)
                        }
                        is LoginResult.DoLoginResult.InFlight -> {
                            previousState.copy(isLoading = true,isError = false, errorMessage = "")
                        }
                    }
                }

                is LoginResult.TypePasswordResult->{
                    previousState.copy(password = result.password,isError = false, errorMessage = "",isLoading = false)
                }

                is LoginResult.TypeUserIdResult->{
                    previousState.copy(userId = result.userId,isError = false, errorMessage = "",isLoading = false)
                }
            }
        }
    }
}