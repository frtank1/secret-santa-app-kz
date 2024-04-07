package kz.secret_santa_jusan.presentation.profile


import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kz.secret_santa_jusan.core.base.CoreBaseViewModel

data class RessetData(
    val name:String? = "",
    val email: String? = "",
    val newPasword: String? = "",
    val repeatPasword: String? = "",
    val showPassword:Boolean = false,
    val errorPassword:Boolean = false,
)
interface IProfileViewModel {
    val state: StateFlow<ProfileState>
    val navigationEvent: StateFlow<NavigationEvent>
    fun sendEvent(event: ProfileEvent)
}

sealed class ProfileEvent{
    object Back: ProfileEvent()
    class EnterLogin(val text: String): ProfileEvent()
    class EnterPassword(val text: String): ProfileEvent()
    class EnterRepeatPassword(val text: String): ProfileEvent()
    class EnterMail(val text: String): ProfileEvent()
    object Delete:ProfileEvent()
    object SaveProfile:ProfileEvent()

    object SavePasword:ProfileEvent()
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

sealed class ProfileState(val ressetData:RessetData){
    class Default( ressetData:RessetData): ProfileState(ressetData)


}

class ProfileViewModelPreview : IProfileViewModel {
    override val state: StateFlow<ProfileState> = MutableStateFlow(ProfileState.Default(RessetData())).asStateFlow()
    override val navigationEvent = MutableStateFlow(NavigationEvent.Default()).asStateFlow()
    override fun sendEvent(event: ProfileEvent) {}
}

class ProfileViewModel(
): CoreBaseViewModel(), IProfileViewModel {

    private var _state = MutableStateFlow<ProfileState>(ProfileState.Default(RessetData()))
    override val state: StateFlow<ProfileState> = _state.asStateFlow()


    private val _navigationEvent = MutableStateFlow<NavigationEvent>(NavigationEvent.Default())
    override val navigationEvent: StateFlow<NavigationEvent> = _navigationEvent.asStateFlow()

    init {
        screenModelScope.launch {
        }
    }

    override fun sendEvent(event: ProfileEvent) {
        when(event){
            ProfileEvent.Back -> {
                _navigationEvent.value = NavigationEvent.Back()
            }

            ProfileEvent.Delete -> {

            }
            ProfileEvent.SaveProfile -> {

            }

            ProfileEvent.SavePasword -> {

            }

            is ProfileEvent.EnterLogin -> {
                _state.value = ProfileState.Default(state.value.ressetData.copy(name  = event.text))
            }
            is ProfileEvent.EnterMail -> {
                _state.value = ProfileState.Default(state.value.ressetData.copy(email  = event.text))
            }
            is ProfileEvent.EnterPassword -> {
                _state.value = ProfileState.Default(state.value.ressetData.copy(newPasword  = event.text))
            }
            is ProfileEvent.EnterRepeatPassword -> {
                _state.value = ProfileState.Default(state.value.ressetData.copy(repeatPasword  = event.text))
            }
        }
    }
}
