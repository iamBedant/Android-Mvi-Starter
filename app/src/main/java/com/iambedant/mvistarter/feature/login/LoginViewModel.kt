package com.iambedant.mvistarter.feature.login

import com.iambedant.mvistarter.feature.base.BaseViewModel
import com.iambedant.mvistarter.mvibase.MviActionProcessorHolder
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.functions.BiFunction

/**
 * Created by @iamBedant on 23/05/18.
 */
class LoginViewModel(private val loginActionProcessorHolder: LoginActionProcessorHolder) :
        BaseViewModel<LoginIntent, LoginViewState, LoginAction, LoginResult>() {

    override fun initialState(): LoginViewState = LoginViewState.idle()
    override fun reducer(): BiFunction<LoginViewState, LoginResult, LoginViewState> = reducer
    override fun actionProcessorHolder(): MviActionProcessorHolder<LoginAction, LoginResult> = loginActionProcessorHolder

    override fun intentFilter(): ObservableTransformer<LoginIntent, LoginIntent> {
        return ObservableTransformer { intents ->
            intents.publish { shared ->
                Observable.merge<LoginIntent>(
                        shared.ofType(LoginIntent.InitialIntent::class.java).take(1),
                        shared.filter { it != LoginIntent.InitialIntent }
                )
            }
        }
    }

    init {
        connectObservableToLiveData()
    }

    override fun actionFromIntent(intent: LoginIntent): LoginAction {
        return when (intent) {
            is LoginIntent.InitialIntent -> LoginAction.LoadLoginAction
            is LoginIntent.DoLoginIntent -> LoginAction.DoLoginAction(intent.userId, intent.password)
            is LoginIntent.typePasswordIntent -> LoginAction.TypePasswordAction(intent.password)
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
                            previousState.copy(isLoading = false, isLoginSuccessful = false, isError = true, errorMessage = result.errorMessage)
                        }
                        is LoginResult.DoLoginResult.InFlight -> {
                            previousState.copy(isLoading = true, isError = false, errorMessage = "")
                        }
                    }
                }

                is LoginResult.TypePasswordResult -> {
                    previousState.copy(password = result.password, isError = false, errorMessage = "", isLoading = false)
                }

                is LoginResult.TypeUserIdResult -> {
                    previousState.copy(userId = result.userId, isError = false, errorMessage = "", isLoading = false)
                }
            }
        }
    }
}