package kz.secret_santa_jusan.data.wishlist.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.text.SimpleDateFormat
import java.util.Date

@Serializable
data class CreateWishlistModel(
    @SerialName("gameId")
    val gameId: String? = null,
    @SerialName("gifts")
    val gifts: List<String>? = null,
)