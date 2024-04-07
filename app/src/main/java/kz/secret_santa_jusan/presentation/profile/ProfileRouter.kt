package kz.secret_santa_jusan.presentation.profile

import cafe.adriel.voyager.core.registry.ScreenProvider

//Регистрируем в MyApp
sealed class ProfileRouter : ScreenProvider {
    object ProfileScreen : ProfileRouter()
}