package kz.secret_santa_jusan.presentation

import androidx.lifecycle.ViewModel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow



interface IMainViewModel {
    val state: StateFlow<MainState>
    fun sendEvent(event: MainEvent)
}

sealed class MainEvent{
    class PermissionNotification(): MainEvent()
    object NeedUpdate: MainEvent()
}

sealed class MainState{
    private var handled: Boolean = false

    fun getValue(): MainState {
        if (handled) return MainState.Default
        handled = true
        return this
    }
    object Default: MainState()
    class PermissionNotification: MainState()
    class NeedUpdate: MainState()
}

class MainViewModelPreview : IMainViewModel {
    override val state: StateFlow<MainState> = MutableStateFlow(MainState.Default).asStateFlow()
    override fun sendEvent(event: MainEvent) {}
}



class MainViewModel (
): ViewModel(), IMainViewModel {

    private var _state = MutableStateFlow<MainState>(MainState.Default)
    override val state: StateFlow<MainState> = _state.asStateFlow()

    override fun sendEvent(event: MainEvent) {
        when(event){
            is MainEvent.PermissionNotification -> {
                _state.value = MainState.PermissionNotification()
            }

            is MainEvent.NeedUpdate -> {
                _state.value = MainState.NeedUpdate()
            }
        }
    }
}