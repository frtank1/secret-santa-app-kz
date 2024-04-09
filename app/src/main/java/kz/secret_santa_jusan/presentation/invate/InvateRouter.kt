package kz.secret_santa_jusan.presentation.invate

import cafe.adriel.voyager.core.registry.ScreenProvider

//Регистрируем в MyApp
sealed class InvateRouter : ScreenProvider {
    object InvateScreen : InvateRouter()
}