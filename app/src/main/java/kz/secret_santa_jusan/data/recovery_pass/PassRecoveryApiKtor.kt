package kz.secret_santa_jusan.data.recovery_pass

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse
import kz.secret_santa_jusan.core.network.KtorConfig
import java.net.URI
import java.net.URLEncoder

class PassRecoveryApiKtor (private val ktorConfig: KtorConfig) {

    suspend fun recovery(email:String): HttpResponse {
        val url = URI("auth/forgot-password")
            .resolve("?email=$email")
            .toString()
        return ktorConfig.httpClient.post(url){
        }

    }
}