package kz.secret_santa_jusan.presentation.invitation

import cafe.adriel.voyager.core.registry.ScreenProvider

sealed class InvitationRouter : ScreenProvider {
    class InvitationScreen(
        val isAuth:Boolean
    ) : InvitationRouter()
}