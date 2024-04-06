package kz.secret_santa_jusan.data.registration.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokenModel(
    @SerialName("accessToken")
    val accessToken: String,
    @SerialName("refreshToken")
    val refreshToken: String
)