package kz.secret_santa_jusan.presentation.main


import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kz.secret_santa_jusan.core.base.CoreBaseViewModel
import kz.secret_santa_jusan.presentation.invate.adding.AddingEvent
import kz.secret_santa_jusan.presentation.invate.adding.AddingState
import kz.secret_santa_jusan.presentation.registration.RegistrationEvent

interface IMainViewModel {
    val state: StateFlow<MainState>
    val navigationEvent: StateFlow<NavigationEvent>
    fun sendEvent(event: MainEvent)
}

sealed class MainEvent{
    object Back: MainEvent()

    object Default:MainEvent()
    class Init(val isAuth: Boolean): MainEvent()

    object GoToRegistration: MainEvent()

    object GoToCreateGame: MainEvent()
}

sealed class NavigationEvent{
    private var handled: Boolean = false

    fun getValue(): NavigationEvent {
        if (handled) return Default()
        handled = true
        return this
    }
    class Default: NavigationEvent()

    object GoToRegistration: NavigationEvent()
    object GoToCreateGame: NavigationEvent()

    class Back: NavigationEvent()

}

sealed class MainState(){
    object NotAuth: MainState()
    object IsAuth: MainState()

}

class MainViewModelPreview : IMainViewModel {
    override val state: StateFlow<MainState> = MutableStateFlow(MainState.NotAuth).asStateFlow()
    override val navigationEvent = MutableStateFlow(NavigationEvent.Default()).asStateFlow()
    override fun sendEvent(event: MainEvent) {}
}

class MainViewModel(
): CoreBaseViewModel(), IMainViewModel {

    private var _state = MutableStateFlow<MainState>(MainState.NotAuth)
    override val state: StateFlow<MainState> = _state.asStateFlow()


    private val _navigationEvent = MutableStateFlow<NavigationEvent>(NavigationEvent.Default())
    override val navigationEvent: StateFlow<NavigationEvent> = _navigationEvent.asStateFlow()


    override fun sendEvent(event: MainEvent) {
        when(event){
            MainEvent.Back -> {
                _navigationEvent.value = NavigationEvent.Back()
            }

            is MainEvent.Init -> {
                if (event.isAuth){
                    _state.value = MainState.IsAuth

                }else{
                    _state.value = MainState.NotAuth
                }
            }

            MainEvent.GoToRegistration -> {
                _navigationEvent.value = NavigationEvent.GoToRegistration
                sendEvent(MainEvent.Default)
            }

            MainEvent.GoToCreateGame -> {
                _navigationEvent.value = NavigationEvent.GoToCreateGame

            }

            MainEvent.Default -> {

            }
        }
    }
}
