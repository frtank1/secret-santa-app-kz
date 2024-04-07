package kz.secret_santa_jusan.data.profile

import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import kz.secret_santa_jusan.core.network.KtorConfig
import kz.secret_santa_jusan.data.profile.models.NewPasswordModel
import kz.secret_santa_jusan.data.profile.models.ProfileModel

class ProfileApiKtor (private val ktorConfig: KtorConfig) {

    suspend fun updateLoginAndMail(profileModel: ProfileModel): HttpResponse {
        return ktorConfig.httpClient.post("settings/update-login-email"){
                setBody(profileModel)
        }
    }

    suspend fun changePassword(newPasword: NewPasswordModel): HttpResponse {
        return ktorConfig.httpClient.post("settings/change-password"){
            setBody(newPasword)
        }
    }

    suspend fun deleteAcaunt(): HttpResponse {
        return ktorConfig.httpClient.delete("settings/delete-accaunt"){
        }
    }


}