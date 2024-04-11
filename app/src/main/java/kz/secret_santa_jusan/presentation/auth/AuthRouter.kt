package kz.secret_santa_jusan.presentation.auth

import cafe.adriel.voyager.core.registry.ScreenProvider
import kz.secret_santa_jusan.data.game.models.GameModel

//Регистрируем в MyApp
sealed class AuthRouter : ScreenProvider {
    class AuthScreen(val gameModel: GameModel) : AuthRouter()
}