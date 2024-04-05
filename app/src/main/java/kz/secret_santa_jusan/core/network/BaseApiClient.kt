package kz.secret_santa_jusan.core.network

import io.ktor.client.statement.HttpResponse

open class BaseApiClient() {
    protected suspend inline fun <reified T> safeApiCall(apiCall: () -> HttpResponse): KtorResponse<T> {

        return try {
            val response = apiCall.invoke()
            if(response.status.value in 200..299){
                KtorResponse.success(response)
            }else {
                KtorResponse.failure<T>(response)
            }
        } catch (e: Exception) {
            KtorResponse.failure(e)
        }
    }
}