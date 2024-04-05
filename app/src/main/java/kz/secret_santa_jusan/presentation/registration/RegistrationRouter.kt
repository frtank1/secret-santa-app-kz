package kz.secret_santa_jusan.presentation.registration

import cafe.adriel.voyager.core.registry.ScreenProvider

//Регистрируем в MyApp
sealed class RegistrationRouter : ScreenProvider {
    object RegistrationScreen : RegistrationRouter()
}