package kz.secret_santa_jusan.presentation.main

import cafe.adriel.voyager.core.registry.ScreenProvider

//Регистрируем в MyApp
sealed class MainRouter : ScreenProvider {
    object MainSreen : MainRouter()
}