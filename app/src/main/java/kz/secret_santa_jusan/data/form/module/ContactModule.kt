package kz.secret_santa_jusan.data.form.module


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ContactModule(
    @SerialName("userName")
    val userName: String?,
    @SerialName("email")
    val email: String?,
    @SerialName("phoneNumber")
    val phoneNumber: String?
)