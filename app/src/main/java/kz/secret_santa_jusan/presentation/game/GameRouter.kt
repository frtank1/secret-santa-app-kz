package kz.secret_santa_jusan.presentation.game

import cafe.adriel.voyager.core.registry.ScreenProvider

//Регистрируем в MyApp
sealed class GameRouter : ScreenProvider {
    object GameScreen : GameRouter()
}