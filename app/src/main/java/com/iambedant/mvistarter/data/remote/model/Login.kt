package com.iambedant.mvistarter.data.remote.model

import com.google.gson.annotations.SerializedName


//Request
data class LoginRequest(@SerializedName("password")
                        val password: String = "",
                        @SerializedName("username")
                        val username: String = "")


//Response
data class LoginResponse(@SerializedName("response_code")
                         val responseCode: String = "",
                         @SerializedName("response")
                         val response: Response,
                         @SerializedName("message")
                         val message: String = "")

data class Response(@SerializedName("name")
                    val name: String = "",
                    @SerializedName("auth_token")
                    val authToken: String = "")