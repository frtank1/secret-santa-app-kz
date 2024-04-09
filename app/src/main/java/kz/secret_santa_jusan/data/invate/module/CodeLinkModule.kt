package kz.secret_santa_jusan.data.invate.module


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CodeLinkModule(
    @SerialName("link")
    val link: String?
)