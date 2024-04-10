package kz.secret_santa_jusan.data.invate.manual

import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import kz.secret_santa_jusan.core.network.KtorConfig
import kz.secret_santa_jusan.data.invate.manual.model.UserNameModel

class ManualAddApiKtor (private val ktorConfig: KtorConfig) {

    suspend fun sendUser(deviceId:String, user:UserNameModel): HttpResponse {
        return ktorConfig.httpClient.post("invitations/send"){
            parameter("deviceId", deviceId)
            setBody(user)
        }
    }
}