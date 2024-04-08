package kz.secret_santa_jusan.data.profile

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kz.secret_santa_jusan.core.network.BaseApiClient
import kz.secret_santa_jusan.core.network.KtorResponse
import kz.secret_santa_jusan.data.profile.models.NewPasswordModel
import kz.secret_santa_jusan.data.example.models.ExampleModel
import kz.secret_santa_jusan.data.profile.models.ProfileModel

class ProfileApiRepository (private val api: ProfileApiKtor): BaseApiClient() {

    suspend fun updateLoginAndMail(profileModel: ProfileModel): KtorResponse<Unit> {
        return withContext(Dispatchers.IO) {
            safeApiCall {
                api.updateLoginAndMail(profileModel)
            }
        }
    }

    suspend fun changePassword(newPasword: NewPasswordModel): KtorResponse<ExampleModel> {
        return withContext(Dispatchers.IO) {
            safeApiCall {
                api.changePassword(newPasword)
            }
        }
    }

    suspend fun deleteAcaunt(): KtorResponse<ExampleModel> {
        return withContext(Dispatchers.IO) {
            safeApiCall {
                api.deleteAcaunt()
            }
        }
    }
}