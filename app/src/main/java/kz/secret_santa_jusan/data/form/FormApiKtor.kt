package kz.secret_santa_jusan.data.form

import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import kz.secret_santa_jusan.core.network.KtorConfig

class FormApiKtor (private val ktorConfig: KtorConfig) {

    suspend fun getDog(): HttpResponse {
        return ktorConfig.httpClient.get(""){
        }
    }
}