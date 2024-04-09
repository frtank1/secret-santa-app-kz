package kz.secret_santa_jusan.core.network

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import kz.secret_santa_jusan.core.network.model.ErrorModel


data class KtorResponse<T>(
    private val status: Status,
    private val _body: T?,
    private val statusCode:Int?,
    val error: ErrorModel? = null,
){

    companion object {
        suspend inline fun <reified T> success(data: HttpResponse): KtorResponse<T> {
            return KtorResponse(
                status = Status.Success,
                _body = data.body<T>(),
                statusCode = data.status.value,
                error = null,
            )
        }

        fun <T> failure(exception: Exception): KtorResponse<T> {
            return KtorResponse(
                status = Status.Failure,
                _body = null,
                statusCode = null,
                error = generateErrorModel(exception),
            )
        }
        suspend inline fun <reified T> failure(data: HttpResponse): KtorResponse<T> {
            return KtorResponse(
                status = Status.Failure,
                _body = null,
                statusCode = null,
                error = data.body<ErrorModel>(),
            )
        }

        private fun generateErrorModel(exception: Exception): ErrorModel {
            return createErrorModel(0, exception.toString())
        }

        private fun createErrorModel(statusCode: Int = 0, customMessage: String? = ""): ErrorModel {
            return ErrorModel(
                status = statusCode,
                message = null,
                customMessage = customMessage?: ""
            )
        }
    }

    val failed: Boolean get() = this.status == Status.Failure

    val isSuccessful: Boolean get() = !failed

    val _statusCode get() = statusCode!!

    val body get() = _body!!

    sealed class Status {
        object Success : Status()
        object Failure : Status()
    }
}