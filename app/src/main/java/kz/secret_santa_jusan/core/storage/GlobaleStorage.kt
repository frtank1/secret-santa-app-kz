package kz.secret_santa_jusan.core.storage

import io.paperdb.Paper
import kz.secret_santa_jusan.core.network.model.TokenModel


class GlobalStorage {
    companion object {
        private val TOKEN = "token"
        private val keys = arrayListOf(TOKEN)

        var applicationId: String = ""

        private var _access_token: String? = null
        val access_token: String get() {
            if(_access_token == null) getAuthToken()
            return _access_token?:""
        }

        private var _refresh_token: String? = null
        val refresh_token: String get() {
            if(_refresh_token == null) getAuthToken()
            return _refresh_token?:""
        }
        fun logOut() {
            _access_token = null
            _refresh_token = null
            keys.forEach { key ->
                Paper.book().delete(key)
            }
        }

        fun saveAuthToken(access_token: String, refresh_token: String){
            _access_token = access_token
            _refresh_token = refresh_token
            Paper.book().write(TOKEN, TokenModel(access_token, refresh_token))
        }

        fun getAuthToken(): TokenModel?{
            val token = Paper.book().read<TokenModel>(TOKEN, null)
            _access_token = token?.access_token
            _refresh_token = token?.refresh_token
            return token
        }

        fun setFlavor(flavor: String){
            Paper.book().write("flavor", flavor)
        }

        fun getFlavor(): String{
            return Paper.book().read("flavor", "")?:""
        }

        fun setBaseUrl(base_url: String){
            Paper.book().write("base_url", base_url)
        }

        val BASE_URL: String get() {
            return Paper.book().read<String>("base_url", "")!!
        }
    }
}