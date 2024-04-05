package kz.secret_santa_jusan.data.example

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kz.secret_santa_jusan.core.network.BaseApiClient
import kz.secret_santa_jusan.core.network.KtorResponse
import kz.secret_santa_jusan.data.example.models.ExampleModel

class ExampleApiRepository (private val api: ExampleApiKtor): BaseApiClient() {

    suspend fun getDog(): KtorResponse<ExampleModel> {
        return withContext(Dispatchers.IO) {
            safeApiCall {
                api.getDog()
            }
        }
    }
}