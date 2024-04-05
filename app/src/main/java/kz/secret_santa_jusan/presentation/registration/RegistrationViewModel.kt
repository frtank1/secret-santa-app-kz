package kz.secret_santa_jusan.presentation.registration


import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kz.secret_santa_jusan.core.base.CoreBaseViewModel

interface IRegistrationViewModel {
    val state: StateFlow<RegistrationState>
    val navigationEvent: StateFlow<NavigationEvent>
    fun sendEvent(event: RegistrationEvent)
}

sealed class RegistrationEvent{
    object Back: RegistrationEvent()
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

sealed class RegistrationState{
    object Default: RegistrationState()
}

class RegistrationViewModelPreview : IRegistrationViewModel {
    override val state: StateFlow<RegistrationState> = MutableStateFlow(RegistrationState.Default).asStateFlow()
    override val navigationEvent = MutableStateFlow(NavigationEvent.Default()).asStateFlow()
    override fun sendEvent(event: RegistrationEvent) {}
}

class ExampleViewModel(
): CoreBaseViewModel(), IRegistrationViewModel {

    private var _state = MutableStateFlow<RegistrationState>(RegistrationState.Default)
    override val state: StateFlow<RegistrationState> = _state.asStateFlow()


    private val _navigationEvent = MutableStateFlow<NavigationEvent>(NavigationEvent.Default())
    override val navigationEvent: StateFlow<NavigationEvent> = _navigationEvent.asStateFlow()

    init {
        screenModelScope.launch {
        }
    }

    override fun sendEvent(event: RegistrationEvent) {
        when(event){
            RegistrationEvent.Back -> {
                _navigationEvent.value = NavigationEvent.Back()
            }
        }
    }
}
