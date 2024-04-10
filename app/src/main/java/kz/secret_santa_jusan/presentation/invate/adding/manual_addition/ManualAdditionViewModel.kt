package kz.secret_santa_jusan.presentation.invate.adding.manual_addition


import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kz.secret_santa_jusan.core.base.CoreBaseViewModel
import kz.secret_santa_jusan.data.invate.manual.ManualAddApiRepository
import kz.secret_santa_jusan.data.profile.ProfileApiRepository

data class User(
    val name:String,
    val email:String
)
interface IManualAdditionViewModel {
    val state: StateFlow<ManualAdditionState>
    val navigationEvent: StateFlow<NavigationEvent>
    fun sendEvent(event: ManualAdditionEvent)
}

sealed class ManualAdditionEvent{
    object Back: ManualAdditionEvent()
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

sealed class ManualAdditionState(val list: List<User>){
    class Default( list: List<User>): ManualAdditionState(list)
}

class ManualAdditionViewModelPreview : IManualAdditionViewModel {
    override val state: StateFlow<ManualAdditionState> = MutableStateFlow(ManualAdditionState.Default(
        emptyList()
    )).asStateFlow()
    override val navigationEvent = MutableStateFlow(NavigationEvent.Default()).asStateFlow()
    override fun sendEvent(event: ManualAdditionEvent) {}
}

class ManualAdditionViewModel(
    private val repository: ManualAddApiRepository
): CoreBaseViewModel(), IManualAdditionViewModel {

    private var _state = MutableStateFlow<ManualAdditionState>(ManualAdditionState.Default(emptyList()))
    override val state: StateFlow<ManualAdditionState> = _state.asStateFlow()


    private val _navigationEvent = MutableStateFlow<NavigationEvent>(NavigationEvent.Default())
    override val navigationEvent: StateFlow<NavigationEvent> = _navigationEvent.asStateFlow()

    init {
        screenModelScope.launch {
        }
    }

    override fun sendEvent(event: ManualAdditionEvent) {
        when(event){
            ManualAdditionEvent.Back -> {
                _navigationEvent.value = NavigationEvent.Back()
            }
        }
    }
}
