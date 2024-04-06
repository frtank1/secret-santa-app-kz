package kz.secret_santa_jusan.data.registration

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import kz.secret_santa_jusan.data.registration.models.RegModel

class RegisterApiKtor (private val httpClient: HttpClient) {
    suspend fun registration(registerModel: RegModel): HttpResponse {
        return httpClient.post("auth/register"){
            setBody(registerModel)
        }
    }
}