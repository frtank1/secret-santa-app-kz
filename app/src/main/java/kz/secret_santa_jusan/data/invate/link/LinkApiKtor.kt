package kz.secret_santa_jusan.data.invate.link

import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse
import kz.secret_santa_jusan.core.network.KtorConfig

class LinkApiKtor (private val ktorConfig: KtorConfig) {

    suspend fun generateLink(id:String): HttpResponse {
        return ktorConfig.httpClient.get("invitations/generate-link?gameId=${id}")
    }
}