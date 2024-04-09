package kz.secret_santa_jusan.data.game.create

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kz.secret_santa_jusan.core.network.BaseApiClient
import kz.secret_santa_jusan.core.network.KtorResponse
import kz.secret_santa_jusan.data.example.models.ExampleModel
import kz.secret_santa_jusan.data.game.create.model.CardModel
import kz.secret_santa_jusan.data.game.create.model.RequestGameModel

class CreateApiRepository (private val api: CreateApiKtor): BaseApiClient() {

    suspend fun create(requestGameModel: RequestGameModel): KtorResponse<CardModel> {
        return withContext(Dispatchers.IO) {
            safeApiCall {
                api.create(requestGameModel)
            }
        }
    }
}