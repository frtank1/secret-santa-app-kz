package kz.secret_santa_jusan.core.network.model

import kotlinx.serialization.Serializable


@Serializable
data class ErrorModel(
    /*val successful: Boolean? = null,
    val message: MessageErrorModel? = null,
    val path: String? = null,*/
    val timestamp: String? = null,
    val status: Int? = null,
    val message: String? = null,
    val customMessage: String? = null,
    val error: String? = null,
    val error_description: String? = null,
)
