package kz.secret_santa_jusan.data.invate

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kz.secret_santa_jusan.core.network.BaseApiClient
import kz.secret_santa_jusan.core.network.KtorResponse
import kz.secret_santa_jusan.data.example.models.ExampleModel
import kz.secret_santa_jusan.data.invate.module.InvatedModule

class InvateApiRepository (private val api: InvateApiKtor): BaseApiClient() {

    suspend fun acceptForInviteLink(code:String): KtorResponse<InvatedModule> {
        return withContext(Dispatchers.IO) {
            safeApiCall {
                api.acceptForInviteLink(code)
            }
        }
    }
}