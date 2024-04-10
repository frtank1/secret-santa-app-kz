package kz.secret_santa_jusan.presentation.invate.adding

import cafe.adriel.voyager.core.registry.ScreenProvider

//Регистрируем в MyApp
sealed class AddingRouter : ScreenProvider {
    class AddingScreen(val id:Int) : AddingRouter()
}