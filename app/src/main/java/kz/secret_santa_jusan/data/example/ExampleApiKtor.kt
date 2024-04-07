package kz.secret_santa_jusan.data.example

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import kz.secret_santa_jusan.core.network.KtorConfig

class ExampleApiKtor (private val ktorConfig: KtorConfig) {

    suspend fun getDog(): HttpResponse {
        return ktorConfig.httpClient.get(""){
        }
    }
}