package kz.secret_santa_jusan.presentation.profile


import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kz.secret_santa_jusan.core.base.CoreBaseViewModel
import kz.secret_santa_jusan.core.storage.GlobalStorage
import kz.secret_santa_jusan.data.profile.ProfileApiRepository
import kz.secret_santa_jusan.data.profile.models.NewPasswordModel
import kz.secret_santa_jusan.data.profile.models.ProfileModel
import kz.secret_santa_jusan.data.registration.RegisterApiRepository
import trikita.log.Log

data class RessetData(
    val name:String? = "",
    val email: String? = "",
    val newPasword: String? = "",
    val repeatPasword: String? = "",
    val showPassword:Boolean = true,
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
    private val repository: ProfileApiRepository
): CoreBaseViewModel(), IProfileViewModel {

    private var _state = MutableStateFlow<ProfileState>(ProfileState.Default(RessetData()))
    override val state: StateFlow<ProfileState> = _state.asStateFlow()


    private val _navigationEvent = MutableStateFlow<NavigationEvent>(NavigationEvent.Default())
    override val navigationEvent: StateFlow<NavigationEvent> = _navigationEvent.asStateFlow()


    override fun sendEvent(event: ProfileEvent) {
        when(event){
            ProfileEvent.Back -> {
                _navigationEvent.value = NavigationEvent.Back()
            }

            ProfileEvent.Delete -> {
                screenModelScope.launch {
                    repository.deleteAcaunt().apply {
                        if(isSuccessful) {
                            Log.d("ok", "delete")
                        }
                    }
                }
            }
            ProfileEvent.SaveProfile -> {
                screenModelScope.launch {
                    val profile = ProfileModel(state.value.ressetData.name,state.value.ressetData.email)
                    repository.updateLoginAndMail(profile).apply {
                        if(isSuccessful) {
                            Log.d("ok", "newProfile")
                        }
                    }
                }
            }

            ProfileEvent.SavePasword -> {
                if (state.value.ressetData.newPasword?.equals(state.value.ressetData.repeatPasword) == true){
                    val password = GlobalStorage.getPassword()
                    val profile = NewPasswordModel(password,state.value.ressetData.newPasword,state.value.ressetData.repeatPasword)
                    screenModelScope.launch {
                        repository.changePassword(profile).apply {
                            if(isSuccessful) {
                                Log.d("ok", "newPasword")
                            }
                        }
                    }
                }else
                {
                    Log.d("ok", "pasword is not corect")
                }
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
