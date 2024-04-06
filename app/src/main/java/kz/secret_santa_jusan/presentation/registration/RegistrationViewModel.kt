package kz.secret_santa_jusan.presentation.registration


import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kz.secret_santa_jusan.core.base.CoreBaseViewModel
import kz.secret_santa_jusan.data.registration.RegisterApiRepository
import kz.secret_santa_jusan.data.registration.models.RegModel
import trikita.log.Log

interface IRegistrationViewModel {
    val state: StateFlow<RegistrationState>
    val navigationEvent: StateFlow<NavigationEvent>
    fun sendEvent(event: RegistrationEvent)
}



sealed class RegistrationEvent{

    object goToRegistration:RegistrationEvent()

    object ClickEnter: RegistrationEvent()
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

sealed class RegistrationState(val regForm: RegModel){
    class Default(regForm: RegModel): RegistrationState(regForm)
    class Registrate(regForm: RegModel): RegistrationState(regForm)
}

class RegistrationViewModelPreview : IRegistrationViewModel {
    override val state: StateFlow<RegistrationState> = MutableStateFlow(RegistrationState.Default(RegModel("","",""))).asStateFlow()
    override val navigationEvent = MutableStateFlow(NavigationEvent.Default()).asStateFlow()
    override fun sendEvent(event: RegistrationEvent) {}
}

class RegistrationViewModel(
    private val repository: RegisterApiRepository
): CoreBaseViewModel(), IRegistrationViewModel {

    private var _state = MutableStateFlow<RegistrationState>(RegistrationState.Default(RegModel("","","")))
    override val state: StateFlow<RegistrationState> = _state.asStateFlow()


    private val _navigationEvent = MutableStateFlow<NavigationEvent>(NavigationEvent.Default())
    override val navigationEvent: StateFlow<NavigationEvent> = _navigationEvent.asStateFlow()

    init {

    }

    override fun sendEvent(event: RegistrationEvent) {
        when(event){
            RegistrationEvent.Back -> {
                _navigationEvent.value = NavigationEvent.Back()
            }

            RegistrationEvent.goToRegistration ->   {
            _state.value = RegistrationState.Registrate(state.value.regForm.copy())
        }

            RegistrationEvent.ClickEnter -> {
                screenModelScope.launch {
                    repository.registration(state.value.regForm).apply {
                        if(isSuccessful) {
                            Log.d("ok", "ok")
                        }
                    }
                }
            }
        }
    }
}
