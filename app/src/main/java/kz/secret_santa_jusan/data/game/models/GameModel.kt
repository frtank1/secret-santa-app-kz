package kz.secret_santa_jusan.data.game.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GameModel(
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