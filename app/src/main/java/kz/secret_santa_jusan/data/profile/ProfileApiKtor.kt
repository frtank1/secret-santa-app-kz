package kz.secret_santa_jusan.data.profile

import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import kz.secret_santa_jusan.data.profile.models.NewPasswordModel
import kz.secret_santa_jusan.data.profile.models.ProfileModel

class ProfileApiKtor (private val httpClient: HttpClient) {

    suspend fun updateLoginAndMail(profileModel: ProfileModel): HttpResponse {
        return httpClient.post("settings/update-login-email"){
                setBody(profileModel)
        }
    }

    suspend fun changePassword(newPasword: NewPasswordModel): HttpResponse {
        return httpClient.post("settings/change-password"){
            setBody(newPasword)
        }
    }

    suspend fun deleteAcaunt(): HttpResponse {
        return httpClient.delete("settings/delete-accaunt"){
        }
    }


}