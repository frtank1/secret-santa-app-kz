package kz.secret_santa_jusan.presentation.invate.adding.manual_addition

import cafe.adriel.voyager.core.registry.ScreenProvider

//Регистрируем в MyApp
sealed class ManualAdditionRouter : ScreenProvider {
    object ManualAdditionScreen : ManualAdditionRouter()
}