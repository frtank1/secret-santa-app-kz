package kz.secret_santa_jusan.data.auth.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthModel(
    @SerialName("email")
    val email: String? = null,
    @SerialName("password")
    val password: String? = null
)