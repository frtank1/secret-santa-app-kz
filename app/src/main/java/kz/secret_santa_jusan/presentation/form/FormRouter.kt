package kz.secret_santa_jusan.presentation.form

import cafe.adriel.voyager.core.registry.ScreenProvider

//Регистрируем в MyApp
sealed class FormRouter : ScreenProvider {
    object FormScreen: FormRouter()
}