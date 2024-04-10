package kz.secret_santa_jusan.data.invate.manual

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kz.secret_santa_jusan.core.network.BaseApiClient
import kz.secret_santa_jusan.core.network.KtorResponse
import kz.secret_santa_jusan.data.example.models.ExampleModel
import kz.secret_santa_jusan.data.invate.manual.model.UserNameModel

class ManualAddApiRepository (private val api: ManualAddApiKtor): BaseApiClient() {

    suspend fun sendUser(deviceId:String,user: List<UserNameModel>): KtorResponse<Unit> {
        return withContext(Dispatchers.IO) {
            safeApiCall {
                api.sendUser(deviceId,user)
            }
        }
    }
}