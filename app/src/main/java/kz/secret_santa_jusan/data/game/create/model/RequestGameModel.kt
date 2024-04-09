package kz.secret_santa_jusan.data.game.create.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestGameModel(
    @SerialName("name")
    val name: String?,
    @SerialName("maxPrice")
    val maxPrice: Int?,
    @SerialName("uniqueIdentifier")
    val uniqueIdentifier: String?,
    @SerialName("priceLimitChecked")
    val priceLimitChecked: Boolean?
)