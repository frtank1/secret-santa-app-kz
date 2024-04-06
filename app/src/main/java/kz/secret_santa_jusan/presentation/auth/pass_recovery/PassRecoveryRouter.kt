package kz.secret_santa_jusan.presentation.auth.pass_recovery

import cafe.adriel.voyager.core.registry.ScreenProvider

//Регистрируем в MyApp
sealed class PassRecoveryRouter : ScreenProvider {
    object PassRecoveryScreen : PassRecoveryRouter()
}