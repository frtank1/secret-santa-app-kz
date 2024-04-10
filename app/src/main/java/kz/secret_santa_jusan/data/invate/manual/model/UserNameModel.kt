package kz.secret_santa_jusan.data.invate.manual.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserNameModel(
    @SerialName("name")
    val name: String?,
    @SerialName("email")
    val email: String?
)