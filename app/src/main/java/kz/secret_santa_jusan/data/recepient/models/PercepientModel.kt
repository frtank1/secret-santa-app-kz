package kz.secret_santa_jusan.data.recepient.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PercepientModel(
    @SerialName("message")
    val message: String?="",
    @SerialName("gifteeEmail")
    val gifteeEmail: String?="",
    @SerialName("wishlistDescriptions")
    val wishlistDescriptions: List<String?>?
)