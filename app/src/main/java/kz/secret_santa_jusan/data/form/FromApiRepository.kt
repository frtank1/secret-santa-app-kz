package kz.secret_santa_jusan.data.form

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kz.secret_santa_jusan.core.network.BaseApiClient
import kz.secret_santa_jusan.core.network.KtorResponse
import kz.secret_santa_jusan.data.example.models.ExampleModel

class FromApiRepository (private val api: FormApiKtor): BaseApiClient() {

    suspend fun getDog(): KtorResponse<ExampleModel> {
        return withContext(Dispatchers.IO) {
            safeApiCall {
                api.getDog()
            }
        }
    }
}