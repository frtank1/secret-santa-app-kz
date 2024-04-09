package kz.secret_santa_jusan.data.game.create

import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import kz.secret_santa_jusan.core.network.KtorConfig
import kz.secret_santa_jusan.data.game.create.model.RequestGameModel

class CreateApiKtor (private val ktorConfig: KtorConfig) {

    suspend fun create(requestGameModel: RequestGameModel): HttpResponse {
        return ktorConfig.httpClient.post("/games/create-game"){
            setBody(requestGameModel)
        }
    }
}