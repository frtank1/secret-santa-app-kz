package kz.secret_santa_jusan.data.form

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kz.secret_santa_jusan.core.network.BaseApiClient
import kz.secret_santa_jusan.core.network.KtorResponse
import kz.secret_santa_jusan.data.example.models.ExampleModel
import kz.secret_santa_jusan.data.form.module.ContactModule

class FromApiRepository (private val api: FormApiKtor): BaseApiClient() {

    suspend fun putContsct(id:String,contact: ContactModule): KtorResponse<Unit> {
        return withContext(Dispatchers.IO) {
            safeApiCall {
                api.putContact(id,contact)
            }
        }
    }
}