package kz.secret_santa_jusan.data.form

import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import kz.secret_santa_jusan.core.network.KtorConfig
import kz.secret_santa_jusan.data.form.module.ContactModule

class FormApiKtor (private val ktorConfig: KtorConfig) {

    suspend fun putContact(id:String,contactModule: ContactModule): HttpResponse {
        return ktorConfig.httpClient.post("gameuser/${id}/contact-info"){
            setBody(contactModule)
        }
    }
}