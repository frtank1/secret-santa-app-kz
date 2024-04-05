package kz.secret_santa_jusan.presentation.auth

import cafe.adriel.voyager.core.registry.ScreenProvider

//Регистрируем в MyApp
sealed class AuthRouter : ScreenProvider {
    object AuthScreen : AuthRouter()
}