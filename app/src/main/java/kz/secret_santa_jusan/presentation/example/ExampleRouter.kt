package kz.secret_santa_jusan.presentation.example

import cafe.adriel.voyager.core.registry.ScreenProvider

//Регистрируем в MyApp
sealed class ExampleRouter : ScreenProvider {
    object AnyScreen : ExampleRouter()
}