package kz.secret_santa_jusan.presentation.invate.adding

import cafe.adriel.voyager.core.registry.ScreenProvider

//Регистрируем в MyApp
sealed class AddingRouter : ScreenProvider {
    object AddingScreen : AddingRouter()
}