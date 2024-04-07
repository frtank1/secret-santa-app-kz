package kz.secret_santa_jusan.data.profile.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileModel(
    @SerialName("newLogin")
    val newLogin: String?,
    @SerialName("newEmail")
    val newEmail: String?
)