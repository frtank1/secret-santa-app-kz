package kz.secret_santa_jusan.data.auth

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import kz.secret_santa_jusan.data.auth.models.AuthModel
import kz.secret_santa_jusan.data.registration.models.RegModel

class  AuthApiKtor (private val httpClient: HttpClient) {

    suspend fun auth(authModel: AuthModel): HttpResponse {
        return httpClient.post("auth/login") {
            setBody(authModel)
        }
    }
}