package kz.secret_santa_jusan.data.game.create.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CardModel(
    @SerialName("id")
    val id: String?,
    @SerialName("name")
    val name: String?,
    @SerialName("maxPrice")
    val maxPrice: Int?,
    @SerialName("participantCount")
    val participantCount: Int?,
    @SerialName("creatorId")
    val creatorId: String?,
    @SerialName("role")
    val role: String?
)