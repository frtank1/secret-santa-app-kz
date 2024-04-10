package kz.secret_santa_jusan.data.invate

import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse
import kz.secret_santa_jusan.core.network.KtorConfig

class InvateApiKtor (private val ktorConfig: KtorConfig) {

    suspend fun acceptForInviteLink(code:String): HttpResponse {
        return ktorConfig.httpClient.post("invitations/accept/"+code){
        }
    }

    suspend fun reShufle(code:String): HttpResponse {
        return ktorConfig.httpClient.post("games/${code}/reshuffle")
    }
}