package kz.secret_santa_jusan.data.invate.module


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InvatedModule(
    @SerialName("gameId")
    val gameId: String?,
    @SerialName("message")
    val message: String?
)