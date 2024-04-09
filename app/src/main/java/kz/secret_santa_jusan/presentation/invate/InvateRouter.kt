package kz.secret_santa_jusan.presentation.invate

import cafe.adriel.voyager.core.registry.ScreenProvider
import kz.secret_santa_jusan.data.game.models.GameModel

//Регистрируем в MyApp
sealed class InvateRouter : ScreenProvider {
    class InvateScreen(val link: String, val gameModel: GameModel) : InvateRouter()
}