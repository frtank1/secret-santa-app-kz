package kz.secret_santa_jusan.data.game.create

import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse
import kz.secret_santa_jusan.core.network.KtorConfig

class CreateApiKtor (private val ktorConfig: KtorConfig) {

    suspend fun create(): HttpResponse {
        return ktorConfig.httpClient.post("/games/create-game"){
        }
    }
}