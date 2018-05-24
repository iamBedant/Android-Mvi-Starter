package com.iambedant.mvistarter.feature.login

import com.iambedant.mvistarter.data.Repository
import com.iambedant.mvistarter.data.remote.model.LoginRequest
import com.iambedant.mvistarter.util.schedulers.BaseSchedulerProvider
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import javax.inject.Inject

/**
 * Created by @iamBedant on 23/05/18.
 */
class LoginActionProcessorHolder @Inject constructor(private val repository: Repository,
                                                     private val schedulerProvider: BaseSchedulerProvider) {
    fun transformFromAction(): ObservableTransformer<LoginAction, LoginResult> {
        return ObservableTransformer { action ->
            action.publish { shared ->
                Observable.merge(
                        shared.ofType(LoginAction.LoadLoginAction::class.java).compose(loadLogin()),
                        shared.ofType(LoginAction.DoLoginAction::class.java).compose(doLogin()),
                        shared.ofType(LoginAction.TypeUserIdAction::class.java).compose(typeUserId()),
                        shared.ofType(LoginAction.TypePasswordAction::class.java).compose(typeUserPassword())
                )
            }

        }
    }

    private fun typeUserId(): ObservableTransformer<LoginAction.TypeUserIdAction, LoginResult.TypeUserIdResult> {
        return ObservableTransformer { action ->
            action.flatMap { Observable.just(LoginResult.TypeUserIdResult(it.userId)) }
        }

    }

    private fun typeUserPassword(): ObservableTransformer<LoginAction.TypePasswordAction, LoginResult.TypePasswordResult> {
        return ObservableTransformer { action ->
            action.flatMap { Observable.just(LoginResult.TypePasswordResult(it.password)) }
        }

    }

    private fun doLogin(): ObservableTransformer<LoginAction.DoLoginAction, LoginResult.DoLoginResult> {
        return ObservableTransformer { action ->
            action.flatMap {
                if(!it.password.isEmpty() && !it.userId.isEmpty()) {
                    repository.doLogin(LoginRequest(username = it.userId, password = it.password))
                            .toObservable()
                            .map { response -> LoginResult.DoLoginResult.Success(response.response.name) }
                            .cast(LoginResult.DoLoginResult::class.java)
                            .onErrorReturn {
                                t -> LoginResult.DoLoginResult.Failure(t.localizedMessage)
                            }
                            .subscribeOn(schedulerProvider.io())
                            .observeOn(schedulerProvider.ui())
                            .startWith(LoginResult.DoLoginResult.InFlight)
                }else{
                    Observable.just(LoginResult.DoLoginResult.Failure("UserId or password can't be empty"))
                }
            }
        }
    }



    private fun loadLogin(): ObservableTransformer<LoginAction.LoadLoginAction, LoginResult.LoadLoginResult> {
        return ObservableTransformer { action ->
            action.flatMap {
                Observable.just(LoginResult.LoadLoginResult.Success("Welcome"))
            }
        }
    }


}