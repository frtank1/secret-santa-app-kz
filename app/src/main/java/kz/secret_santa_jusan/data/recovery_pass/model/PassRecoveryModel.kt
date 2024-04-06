package kz.secret_santa_jusan.data.recovery_pass.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PassRecoveryModel(
    @SerialName("message")
    val message: String? = null,
)