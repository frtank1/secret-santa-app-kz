package kz.secret_santa_jusan.presentation.auth


import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kz.secret_santa_jusan.core.base.CoreBaseViewModel

interface IAuthViewModel {
    val state: StateFlow<AuthState>
    val navigationEvent: StateFlow<NavigationEvent>
    fun sendEvent(event: AuthEvent)
}

sealed class AuthEvent{
    object Back: AuthEvent()
}

sealed class NavigationEvent{
    private var handled: Boolean = false

    fun getValue(): NavigationEvent {
        if (handled) return Default()
        handled = true
        return this
    }
    class Default: NavigationEvent()
    class Back: NavigationEvent()
}

sealed class AuthState{
    object Default: AuthState()
}

class AuthViewModelPreview : IAuthViewModel {
    override val state: StateFlow<AuthState> = MutableStateFlow(AuthState.Default).asStateFlow()
    override val navigationEvent = MutableStateFlow(NavigationEvent.Default()).asStateFlow()
    override fun sendEvent(event: AuthEvent) {}
}

class AuthViewModel(
): CoreBaseViewModel(), IAuthViewModel {

    private var _state = MutableStateFlow<AuthState>(AuthState.Default)
    override val state: StateFlow<AuthState> = _state.asStateFlow()


    private val _navigationEvent = MutableStateFlow<NavigationEvent>(NavigationEvent.Default())
    override val navigationEvent: StateFlow<NavigationEvent> = _navigationEvent.asStateFlow()

    init {
        screenModelScope.launch {
        }
    }

    override fun sendEvent(event: AuthEvent) {
        when(event){
            AuthEvent.Back -> {
                _navigationEvent.value = NavigationEvent.Back()
            }
        }
    }
}
