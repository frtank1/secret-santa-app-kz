package kz.secret_santa_jusan.data.recovery_pass

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kz.secret_santa_jusan.core.network.BaseApiClient
import kz.secret_santa_jusan.core.network.KtorResponse
import kz.secret_santa_jusan.data.example.models.ExampleModel
import kz.secret_santa_jusan.data.recovery_pass.model.PassRecoveryModel

class PassRecoveryApiRepository (private val api: PassRecoveryApiKtor): BaseApiClient() {

    suspend fun recovery(email:String): KtorResponse<PassRecoveryModel> {
        return withContext(Dispatchers.IO) {

            safeApiCall {
                api.recovery(email)
            }
        }
    }
}