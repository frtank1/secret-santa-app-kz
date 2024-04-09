package kz.secret_santa_jusan.data.game.models


import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Parcelize
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
    val role: String?,
    @SerialName("status")
    val status: String?
) : Parcelable

enum class GameStatus{
    INPROCESS,MATCHCOMPLETED
}