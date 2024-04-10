package kz.secret_santa_jusan.presentation.recepient

import cafe.adriel.voyager.core.registry.ScreenProvider
import kz.secret_santa_jusan.data.game.models.GameModel

//Регистрируем в MyApp
sealed class RecepientRouter : ScreenProvider {
    class RecepientScreen(val gameModel: GameModel) : RecepientRouter()
}