package kz.secret_santa_jusan.data.recepient

import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse
import kz.secret_santa_jusan.core.network.KtorConfig

class RecepientApiKtor (private val ktorConfig: KtorConfig) {

    suspend fun getPercepient( id:String): HttpResponse {
        return ktorConfig.httpClient.get("wishlist/${id}/my-giftee-wishlist"){
        }
    }
}