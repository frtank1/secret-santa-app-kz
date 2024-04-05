package kz.secret_santa_jusan.data.example.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExampleModel(
    @SerialName("message")
    val message: String? = null,
    @SerialName("status")
    val status: String? = null
)