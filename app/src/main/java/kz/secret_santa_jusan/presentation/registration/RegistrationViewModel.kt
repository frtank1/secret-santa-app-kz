package kz.secret_santa_jusan.presentation.registration


import android.widget.Toast
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kz.secret_santa_jusan.core.base.CoreBaseViewModel
import kz.secret_santa_jusan.core.storage.GlobalStorage
import kz.secret_santa_jusan.data.registration.RegisterApiRepository
import kz.secret_santa_jusan.data.registration.models.RegModel
import trikita.log.Log

interface IRegistrationViewModel {
    val state: StateFlow<RegistrationState>
    val navigationEvent: StateFlow<NavigationEvent>
    fun sendEvent(event: RegistrationEvent)
}



sealed class RegistrationEvent{

    object GoToAuth:RegistrationEvent()

    class EnterLogin(val text: String): RegistrationEvent()
    class EnterPassword(val text: String): RegistrationEvent()
    class EnterMail(val text: String): RegistrationEvent()

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
    object GoToAuth:NavigationEvent()
    object GoToMain:NavigationEvent()
}

sealed class RegistrationState(val regForm: RegModel){
    class Default(regForm: RegModel): RegistrationState(regForm)
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


    override fun sendEvent(event: RegistrationEvent) {
        when(event){
            RegistrationEvent.Back -> {
                _navigationEvent.value = NavigationEvent.Back()
            }

            RegistrationEvent.ClickEnter -> {
                screenModelScope.launch {
                    repository.registration(state.value.regForm).apply {
                        if(isSuccessful) {
                            Log.d("ok", "ok")
                            GlobalStorage.saveAuthToken(body.accessToken, body.refreshToken)
                            GlobalStorage.saveUser(state.value.regForm)
                            _navigationEvent.value = NavigationEvent.GoToMain
                        }
                    }
                }
            }

            is RegistrationEvent.EnterLogin -> {
                _state.value = RegistrationState.Default(state.value.regForm.copy(login = event.text))
            }
            is RegistrationEvent.EnterMail -> {
                _state.value = RegistrationState.Default(state.value.regForm.copy(email = event.text))
            }
            is RegistrationEvent.EnterPassword -> {
                _state.value = RegistrationState.Default(state.value.regForm.copy(password = event.text))
            }

            RegistrationEvent.GoToAuth -> {
               _navigationEvent.value = NavigationEvent.GoToAuth
            }
        }
    }
}
