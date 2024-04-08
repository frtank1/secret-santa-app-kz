package kz.secret_santa_jusan.data.game

import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import kz.secret_santa_jusan.core.network.KtorConfig

class GameApiKtor (private val ktorConfig: KtorConfig) {

    suspend fun myGames(): HttpResponse {
        return ktorConfig.httpClient.get("/games/mygames"){
        }
    }
}