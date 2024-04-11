package kz.secret_santa_jusan.presentation.registration

import cafe.adriel.voyager.core.registry.ScreenProvider
import kz.secret_santa_jusan.data.game.models.GameModel

//Регистрируем в MyApp
sealed class RegistrationRouter : ScreenProvider {
    class RegistrationScreen(val gameModel: GameModel) : RegistrationRouter()
}