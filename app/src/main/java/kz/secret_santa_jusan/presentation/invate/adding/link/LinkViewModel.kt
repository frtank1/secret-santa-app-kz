package kz.secret_santa_jusan.presentation.invate.adding.link


import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kz.secret_santa_jusan.core.base.CoreBaseViewModel

interface ILinkViewModel {
    val state: StateFlow<LinkState>
    val navigationEvent: StateFlow<NavigationEvent>
    fun sendEvent(event: LinkEvent)
}

sealed class LinkEvent{
    object Back: LinkEvent()
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

sealed class LinkState{
    object Default: LinkState()
}

class LinkViewModelPreview : ILinkViewModel {
    override val state: StateFlow<LinkState> = MutableStateFlow(LinkState.Default).asStateFlow()
    override val navigationEvent = MutableStateFlow(NavigationEvent.Default()).asStateFlow()
    override fun sendEvent(event: LinkEvent) {}
}

class LinkViewModel(
): CoreBaseViewModel(), ILinkViewModel {

    private var _state = MutableStateFlow<LinkState>(LinkState.Default)
    override val state: StateFlow<LinkState> = _state.asStateFlow()


    private val _navigationEvent = MutableStateFlow<NavigationEvent>(NavigationEvent.Default())
    override val navigationEvent: StateFlow<NavigationEvent> = _navigationEvent.asStateFlow()

    init {
        screenModelScope.launch {
        }
    }

    override fun sendEvent(event: LinkEvent) {
        when(event){
            LinkEvent.Back -> {
                _navigationEvent.value = NavigationEvent.Back()
            }
        }
    }
}
