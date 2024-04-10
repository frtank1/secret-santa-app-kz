package kz.secret_santa_jusan.data.recepient

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kz.secret_santa_jusan.core.network.BaseApiClient
import kz.secret_santa_jusan.core.network.KtorResponse
import kz.secret_santa_jusan.data.example.models.ExampleModel
import kz.secret_santa_jusan.data.recepient.models.PercepientModel

class RecepientApiRepository (private val api: RecepientApiKtor): BaseApiClient() {

    suspend fun getPercepient( id:String): KtorResponse<PercepientModel> {
        return withContext(Dispatchers.IO) {
            safeApiCall {
                api.getPercepient( id)
            }
        }
    }
}