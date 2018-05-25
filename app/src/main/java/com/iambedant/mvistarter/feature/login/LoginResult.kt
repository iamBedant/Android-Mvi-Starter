package com.iambedant.mvistarter.feature.login

import com.iambedant.mvistarter.mvibase.MviResult


/**
 * Created by @iamBedant on 23/05/18.
 */
sealed class LoginResult : MviResult {
    sealed class DoLoginResult : LoginResult() {
        data class Success(val username: String) : DoLoginResult()
        data class Failure(val errorMessage: String) : DoLoginResult()
        object InFlight : DoLoginResult()
    }

    sealed class LoadLoginResult : LoginResult() {
        data class Success(val welcomeMessage: String) : LoadLoginResult()
    }

    data class TypePasswordResult(val password: String) : LoginResult()

    data class TypeUserIdResult(val userId: String) : LoginResult()
}