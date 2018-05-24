package com.iambedant.mvistarter.feature.login

import com.iambedant.pizzaapp.mvibase.MviIntent

/**
 * Created by @iamBedant on 23/05/18.
 */
sealed class LoginIntent : MviIntent {
    object InitialIntent : LoginIntent()
    data class DoLoginIntent(val userId: String, val password: String) : LoginIntent()
    data class typeUserIdIntent(val userId: String) : LoginIntent()
    data class typePasswordIntent(val password: String) : LoginIntent()

}