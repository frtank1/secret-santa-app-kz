package kz.secret_santa_jusan.core.navigation

import cafe.adriel.voyager.core.lifecycle.ScreenDisposable
import cafe.adriel.voyager.core.screen.Screen
import kz.secret_santa_jusan.core.base.CoreBaseScreen

class ScreenLifecycleOwner : ScreenDisposable {
    override fun onDispose(screen: Screen) {
        println("My ${screen} is being disposed")
        when(screen){
            is CoreBaseScreen -> {
                screen.onDispose()
            }
        }
    }
}