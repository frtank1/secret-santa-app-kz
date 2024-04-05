package kz.secret_santa_jusan.core.network

import org.koin.dsl.module
import trikita.log.Log
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kz.secret_santa_jusan.core.storage.GlobalStorage

@OptIn(ExperimentalSerializationApi::class)
val httpClientModule = module {
    single {
        HttpClient(Android).config {
            defaultRequest {
                url(GlobalStorage.BASE_URL)
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
            }
            install(Logging) {
                level = LogLevel.ALL
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.i("HttpClient", message)
                    }
                }
            }
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                    explicitNulls = true
                })
            }
            install(Auth) {
                bearer {
                    loadTokens {
                        BearerTokens(
                            GlobalStorage.access_token,
                            GlobalStorage.refresh_token
                        )
                    }
                    /*sendWithoutRequest { request ->
                       // !request.url.toString().contains("")
                    }*/
                    /*refreshTokens {

                        запрос на переавторизацию
                        if (response.status.value >= 200 && response.status.value < 300) {
                            val token = response.body<TokenModel>()
                            GlobalStorage.saveAuthToken(
                                token.access_token,
                                token.refresh_token
                            )
                            BearerTokens(
                                accessToken = token.access_token,
                                refreshToken = token.refresh_token
                            )
                        } else {
                            CoreApp.logOut(true)
                            throw RuntimeException("failed to refresh tokens")
                        }
                    }*/
                }
            }
        }
        /* httpClient.plugin(HttpSend).intercept {request ->
             val originalCall = execute(request)
             if (originalCall.response.status.value == 400) {
                 CoreApp.logOut()
                 originalCall
             } else {
                 originalCall
             }
         }*/
    }
}
