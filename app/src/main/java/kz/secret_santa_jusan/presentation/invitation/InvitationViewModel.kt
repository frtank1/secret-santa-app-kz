import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kz.secret_santa_jusan.core.base.CoreBaseViewModel

class InvitationViewModel : CoreBaseViewModel(), IInvitationViewModel {
    private val _state = MutableStateFlow<InvitationState>(InvitationState.Init)
    override val state: StateFlow<InvitationState> = _state.asStateFlow()

    private val _navigationEvent = MutableStateFlow<NavigationEvent>(NavigationEvent.Default())
    override val navigationEvent: StateFlow<NavigationEvent> = _navigationEvent.asStateFlow()

    override fun sendEvent(event: InvitationEvent) {
        when (event) {
            is InvitationEvent.Init -> {
            }
            is InvitationEvent.Back -> {
                _navigationEvent.value = NavigationEvent.Back()
            }
            is InvitationEvent.GoToForm -> {
                _navigationEvent.value = NavigationEvent.GoToRegistration
            }
            else -> {}
        }
    }
}

interface IInvitationViewModel {
    val state: StateFlow<InvitationState>
    val navigationEvent: StateFlow<NavigationEvent>
    fun sendEvent(event: InvitationEvent)
}

sealed class InvitationEvent {
    object Init : InvitationEvent()
    object Back : InvitationEvent()
    object GoToForm : InvitationEvent()
}

sealed class InvitationState {
    object Init : InvitationState()
}

sealed class NavigationEvent {
    private var handled: Boolean = false

    fun getValue(): NavigationEvent {
        if (handled) return Default()
        handled = true
        return this
    }

    class Default : NavigationEvent()
    class Back : NavigationEvent()
    object GoToRegistration : NavigationEvent()
}
