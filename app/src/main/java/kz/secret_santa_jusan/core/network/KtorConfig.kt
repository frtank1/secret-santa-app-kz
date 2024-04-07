package kz.secret_santa_jusan.core.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerAuthProvider
import io.ktor.client.plugins.plugin
import org.koin.dsl.module

val ktorConfigModel = module { single {
    KtorConfig(get())

} }

class KtorConfig (
    val httpClient: HttpClient,
){
    fun logout(){
        httpClient.plugin(Auth).providers.filterIsInstance<BearerAuthProvider>()
            .firstOrNull()?.clearToken()
    }
}