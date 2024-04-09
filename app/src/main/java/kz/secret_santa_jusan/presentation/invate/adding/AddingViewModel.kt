package kz.secret_santa_jusan.presentation.invate.adding


import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kz.secret_santa_jusan.core.base.CoreBaseViewModel

interface IAddingViewModel {
    val state: StateFlow<AddingState>
    val navigationEvent: StateFlow<NavigationEvent>
    fun sendEvent(event: AddingEvent)
}

sealed class AddingEvent{
    object Back: AddingEvent()
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

sealed class AddingState{
    object Default: AddingState()
}

class AddingViewModelPreview : IAddingViewModel {
    override val state: StateFlow<AddingState> = MutableStateFlow(AddingState.Default).asStateFlow()
    override val navigationEvent = MutableStateFlow(NavigationEvent.Default()).asStateFlow()
    override fun sendEvent(event: AddingEvent) {}
}

class AddingViewModel(
): CoreBaseViewModel(), IAddingViewModel {

    private var _state = MutableStateFlow<AddingState>(AddingState.Default)
    override val state: StateFlow<AddingState> = _state.asStateFlow()


    private val _navigationEvent = MutableStateFlow<NavigationEvent>(NavigationEvent.Default())
    override val navigationEvent: StateFlow<NavigationEvent> = _navigationEvent.asStateFlow()

    init {
        screenModelScope.launch {
        }
    }

    override fun sendEvent(event: AddingEvent) {
        when(event){
            AddingEvent.Back -> {
                _navigationEvent.value = NavigationEvent.Back()
            }
        }
    }
}
