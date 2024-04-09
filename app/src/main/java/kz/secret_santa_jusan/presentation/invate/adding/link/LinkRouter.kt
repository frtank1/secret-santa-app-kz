package kz.secret_santa_jusan.presentation.invate.adding.link

import cafe.adriel.voyager.core.registry.ScreenProvider

//Регистрируем в MyApp
sealed class LinkRouter : ScreenProvider {
    object LinkScreen : LinkRouter()
}