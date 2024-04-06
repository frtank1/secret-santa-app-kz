package kz.secret_santa_jusan.data.auth

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kz.secret_santa_jusan.core.network.BaseApiClient
import kz.secret_santa_jusan.core.network.KtorResponse
import kz.secret_santa_jusan.data.auth.models.AuthModel
import kz.secret_santa_jusan.data.example.models.ExampleModel
import kz.secret_santa_jusan.data.registration.models.TokenModel

class AuthApiRepository (private val api: AuthApiKtor): BaseApiClient() {

    suspend fun auth(authModel: AuthModel): KtorResponse<TokenModel> {
        return withContext(Dispatchers.IO) {
            safeApiCall {
                api.auth(authModel)
            }
        }
    }
}