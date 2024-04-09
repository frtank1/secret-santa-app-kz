package kz.secret_santa_jusan.data.game.create

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kz.secret_santa_jusan.core.network.BaseApiClient
import kz.secret_santa_jusan.core.network.KtorResponse
import kz.secret_santa_jusan.data.game.create.model.RequestGameModel
import kz.secret_santa_jusan.data.game.models.GameModel

class CreateApiRepository (private val api: CreateApiKtor): BaseApiClient() {

    suspend fun create(requestGameModel: RequestGameModel): KtorResponse<GameModel> {
        return withContext(Dispatchers.IO) {
            safeApiCall {
                api.create(requestGameModel)
            }
        }
    }
}