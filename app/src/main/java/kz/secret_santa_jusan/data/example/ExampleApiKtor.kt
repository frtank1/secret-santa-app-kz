package kz.secret_santa_jusan.data.example

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse

class ExampleApiKtor (private val httpClient: HttpClient) {

    suspend fun getDog(): HttpResponse {
        return httpClient.get(""){
        }
    }
}