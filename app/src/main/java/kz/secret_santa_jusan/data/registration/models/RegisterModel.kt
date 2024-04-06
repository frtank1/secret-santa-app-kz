package kz.secret_santa_jusan.data.registration.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.text.SimpleDateFormat
import java.util.Date

@Serializable
data class RegModel(
    @SerialName("login")
    val login: String? = null,
    @SerialName("email")
    val email: String? = null,
    @SerialName("password")
    val password: String? = null
)