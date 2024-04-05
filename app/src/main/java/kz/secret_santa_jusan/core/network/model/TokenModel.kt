package kz.secret_santa_jusan.core.network.model

@kotlinx.serialization.Serializable
data class TokenModel(
    val access_token: String,
    val refresh_token: String,
)