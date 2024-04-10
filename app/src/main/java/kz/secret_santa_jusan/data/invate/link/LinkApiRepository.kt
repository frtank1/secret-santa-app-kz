package kz.secret_santa_jusan.data.invate.link

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kz.secret_santa_jusan.core.network.BaseApiClient
import kz.secret_santa_jusan.core.network.KtorResponse
import kz.secret_santa_jusan.data.example.models.ExampleModel
import kz.secret_santa_jusan.data.invate.module.CodeLinkModule

class LinkApiRepository (private val api: LinkApiKtor): BaseApiClient() {

    suspend fun generateLink(id:String): KtorResponse<CodeLinkModule> {
        return withContext(Dispatchers.IO) {
            safeApiCall {
                api.generateLink(id)
            }
        }
    }
}