package kz.secret_santa_jusan.presentation.profile


import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kz.secret_santa_jusan.core.base.CoreBaseViewModel

interface IProfileViewModel {
    val state: StateFlow<ProfileState>
    val navigationEvent: StateFlow<NavigationEvent>
    fun sendEvent(event: ProfileEvent)
}

sealed class ProfileEvent{
    object Back: ProfileEvent()
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

sealed class ProfileState{
    object Default: ProfileState()
}

class ProfileViewModelPreview : IProfileViewModel {
    override val state: StateFlow<ProfileState> = MutableStateFlow(ProfileState.Default).asStateFlow()
    override val navigationEvent = MutableStateFlow(NavigationEvent.Default()).asStateFlow()
    override fun sendEvent(event: ProfileEvent) {}
}

class ProfileViewModel(
): CoreBaseViewModel(), IProfileViewModel {

    private var _state = MutableStateFlow<ProfileState>(ProfileState.Default)
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
        }
    }
}
