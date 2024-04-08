package kz.secret_santa_jusan.data.game.create

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kz.secret_santa_jusan.core.network.BaseApiClient
import kz.secret_santa_jusan.core.network.KtorResponse
import kz.secret_santa_jusan.data.example.models.ExampleModel
import kz.secret_santa_jusan.data.game.create.model.CardModel

class CreateApiRepository (private val api: CreateApiKtor): BaseApiClient() {

    suspend fun create(): KtorResponse<CardModel> {
        return withContext(Dispatchers.IO) {
            safeApiCall {
                api.create()
            }
        }
    }
}