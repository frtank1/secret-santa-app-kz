package kz.secret_santa_jusan.data.game

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kz.secret_santa_jusan.core.network.BaseApiClient
import kz.secret_santa_jusan.core.network.KtorResponse

class GameApiRepository (private val api: GameApiKtor): BaseApiClient() {

    suspend fun myGames(): KtorResponse<List<GameModel>> {
        return withContext(Dispatchers.IO) {
            safeApiCall {
                api.myGames()
            }
        }
    }
}