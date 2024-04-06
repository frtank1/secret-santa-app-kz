package kz.secret_santa_jusan.presentation.auth.pass_recovery


import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kz.secret_santa_jusan.core.base.CoreBaseViewModel
import kz.secret_santa_jusan.core.storage.GlobalStorage
import kz.secret_santa_jusan.data.auth.AuthApiRepository
import kz.secret_santa_jusan.data.auth.models.AuthModel
import kz.secret_santa_jusan.data.recovery_pass.PassRecoveryApiRepository
import kz.secret_santa_jusan.presentation.auth.AuthEvent
import kz.secret_santa_jusan.presentation.registration.RegistrationEvent
import kz.secret_santa_jusan.presentation.registration.RegistrationState
import trikita.log.Log

interface IPassRecoveryViewModel {
    val state: StateFlow<PassRecoveryState>
    val navigationEvent: StateFlow<NavigationEvent>
    fun sendEvent(event: PassRecoveryEvent)
}

sealed class PassRecoveryEvent{
    object Back: PassRecoveryEvent()
    class EnterEmail(val text: String): PassRecoveryEvent()
    object ClickEnter: PassRecoveryEvent()
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

sealed class PassRecoveryState(val email: String){
    class Default(email: String): PassRecoveryState(email)

    class MessegeSended(email: String):PassRecoveryState(email)
}

class PassRecoveryViewModelPreview : IPassRecoveryViewModel {
    override val state: StateFlow<PassRecoveryState> = MutableStateFlow(PassRecoveryState.Default("")).asStateFlow()
    override val navigationEvent = MutableStateFlow(NavigationEvent.Default()).asStateFlow()
    override fun sendEvent(event: PassRecoveryEvent) {}
}

class PassRecoveryViewModel(
    private val repository: PassRecoveryApiRepository
): CoreBaseViewModel(), IPassRecoveryViewModel {

    private var _state = MutableStateFlow<PassRecoveryState>(PassRecoveryState.Default(""))
    override val state: StateFlow<PassRecoveryState> = _state.asStateFlow()


    private val _navigationEvent = MutableStateFlow<NavigationEvent>(NavigationEvent.Default())
    override val navigationEvent: StateFlow<NavigationEvent> = _navigationEvent.asStateFlow()

    init {
        screenModelScope.launch {
        }
    }

    override fun sendEvent(event: PassRecoveryEvent) {
        when(event){
            PassRecoveryEvent.Back -> {
                _navigationEvent.value = NavigationEvent.Back()
            }

            is PassRecoveryEvent.EnterEmail -> {
                _state.value = PassRecoveryState.Default(event.text)
            }

            PassRecoveryEvent.ClickEnter -> {
                screenModelScope.launch {
                    repository.recovery(state.value.email).apply {
                        if(isSuccessful) {
                            Log.d("ok", body.message)
                            _state.value = PassRecoveryState.MessegeSended(state.value.email)
                        }
                    }
                }
            }
        }
    }
}
