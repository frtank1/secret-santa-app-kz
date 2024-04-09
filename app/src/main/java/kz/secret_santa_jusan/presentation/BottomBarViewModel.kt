package kz.secret_santa_jusan.presentation


import androidx.lifecycle.ViewModel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Timer


interface IBottomBarViewModel {
    val state: StateFlow<BottomBarState>
    val navigationEvent: StateFlow<NavigationEvent>
    fun sendEvent(event: BottomBarEvent)
}

sealed class NavigationEvent{
    private var handled: Boolean = false

    fun getValue(): NavigationEvent {
        if (handled) return Default()
        handled = true
        return this
    }
    class Default: NavigationEvent()
    class MainPage: NavigationEvent()
    class Game: NavigationEvent()
    class Profile: NavigationEvent()
}

sealed class BottomBarEvent{
    class ClickMainPage(val needOpen: Boolean): BottomBarEvent()

    object ClickProfile: BottomBarEvent()
    object ClickGame: BottomBarEvent()
    object HideBottomBar: BottomBarEvent()
    object ShowBottomBar: BottomBarEvent()
}

sealed class BottomBarState(val bottomBar: BottomBar){
    class Default(bottomBar: BottomBar): BottomBarState(bottomBar)
}

data class BottomBar(
    val selectMainPage: Boolean,
    val selectGame: Boolean,
    val selectProfile: Boolean,
    val showingBottomBar: Boolean = true,
)
class BottomBarViewModelPreview : IBottomBarViewModel {
    override var state = MutableStateFlow<BottomBarState>(BottomBarState.Default(
        BottomBar(true, false, false, false))
    )
    override val navigationEvent = MutableStateFlow<NavigationEvent>(NavigationEvent.Default())
    override fun sendEvent(event: BottomBarEvent) {}
}


class BottomBarViewModel (
): ViewModel(), IBottomBarViewModel {
    private var _state = MutableStateFlow<BottomBarState>(BottomBarState.Default(
        BottomBar(true, false, false, false))
    )
    override val state: StateFlow<BottomBarState> = _state.asStateFlow()

    private val _navigationEvent = MutableStateFlow<NavigationEvent>(NavigationEvent.Default())
    override val navigationEvent: StateFlow<NavigationEvent> = _navigationEvent.asStateFlow()

    private var timer: Timer? = null


    override fun onCleared() {
        if(timer != null) {
            timer!!.cancel()
            timer = null
        }
        super.onCleared()
    }

    override fun sendEvent(event: BottomBarEvent) {
        when(event) {
            is BottomBarEvent.ClickMainPage -> {
                _state.value = BottomBarState.Default(
                    state.value.bottomBar.copy(true, false, false, true)
                )
                if(event.needOpen) {
                    _navigationEvent.value = NavigationEvent.MainPage()
                }
            }
            BottomBarEvent.ClickProfile -> {
                _state.value = BottomBarState.Default(
                    state.value.bottomBar.copy(false, false, true, true)
                )
                _navigationEvent.value = NavigationEvent.Profile()
            }

            BottomBarEvent.HideBottomBar -> {
                _state.value = BottomBarState.Default(
                    state.value.bottomBar.copy(showingBottomBar = false)
                )
            }
            BottomBarEvent.ShowBottomBar -> {
                _state.value = BottomBarState.Default(
                    state.value.bottomBar.copy(showingBottomBar = true)
                )
            }

            BottomBarEvent.ClickGame -> {
                _state.value = BottomBarState.Default(
                    state.value.bottomBar.copy(false, true, false, true)
                )
                _navigationEvent.value = NavigationEvent.Game()
            }
        }
    }
}