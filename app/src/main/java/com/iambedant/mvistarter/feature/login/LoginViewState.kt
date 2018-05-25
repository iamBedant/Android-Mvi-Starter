package com.iambedant.mvistarter.feature.login

import com.iambedant.mvistarter.mvibase.MviViewState


/**
 * Created by @iamBedant on 23/05/18.
 */
data class LoginViewState(val isLoading: Boolean,
                          val userId: String,
                          val password: String,
                          val errorMessage: String,
                          val isError: Boolean,
                          val isLoginSuccessful: Boolean
) : MviViewState {


    companion object {
        fun idle(): LoginViewState {
            return LoginViewState(
                    isLoading = false,
                    userId = "",
                    password = "",
                    isError = false,
                    errorMessage = "",
                    isLoginSuccessful = false
            )
        }
    }
}