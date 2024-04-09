package kz.secret_santa_jusan.presentation.invate.adding.link


import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kz.secret_santa_jusan.core.base.CoreBaseViewModel

interface IFindViewModel {
    val state: StateFlow<FindState>
    val navigationEvent: StateFlow<NavigationEvent>
    fun sendEvent(event: FindEvent)
}

sealed class FindEvent{
    object Back: FindEvent()
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

sealed class FindState{
    object Default: FindState()
}

class FindViewModelPreview : IFindViewModel {
    override val state: StateFlow<FindState> = MutableStateFlow(FindState.Default).asStateFlow()
    override val navigationEvent = MutableStateFlow(NavigationEvent.Default()).asStateFlow()
    override fun sendEvent(event: FindEvent) {}
}

class FindViewModel(
): CoreBaseViewModel(), IFindViewModel {

    private var _state = MutableStateFlow<FindState>(FindState.Default)
    override val state: StateFlow<FindState> = _state.asStateFlow()


    private val _navigationEvent = MutableStateFlow<NavigationEvent>(NavigationEvent.Default())
    override val navigationEvent: StateFlow<NavigationEvent> = _navigationEvent.asStateFlow()

    init {
        screenModelScope.launch {
        }
    }

    override fun sendEvent(event: FindEvent) {
        when(event){
            FindEvent.Back -> {
                _navigationEvent.value = NavigationEvent.Back()
            }
        }
    }
}
