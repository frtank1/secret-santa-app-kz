package kz.secret_santa_jusan.data.profile.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewPasswordModel(
    @SerialName("currentPassword")
    val currentPassword: String?,
    @SerialName("newPassword")
    val newPassword: String?,
    @SerialName("confirmPassword")
    val confirmPassword: String?
)