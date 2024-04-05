package kz.secret_santa_jusan.presentation.example


import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kz.secret_santa_jusan.core.base.CoreBaseViewModel

interface IExampleViewModel {
    val state: StateFlow<ExampleState>
    val navigationEvent: StateFlow<NavigationEvent>
    fun sendEvent(event: ExampleEvent)
}

sealed class ExampleEvent{
    object Back: ExampleEvent()
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

sealed class ExampleState{
    object Default: ExampleState()
}

class ExampleViewModelPreview : IExampleViewModel {
    override val state: StateFlow<ExampleState> = MutableStateFlow(ExampleState.Default).asStateFlow()
    override val navigationEvent = MutableStateFlow(NavigationEvent.Default()).asStateFlow()
    override fun sendEvent(event: ExampleEvent) {}
}

class ExampleViewModel(
): CoreBaseViewModel(), IExampleViewModel {

    private var _state = MutableStateFlow<ExampleState>(ExampleState.Default)
    override val state: StateFlow<ExampleState> = _state.asStateFlow()


    private val _navigationEvent = MutableStateFlow<NavigationEvent>(NavigationEvent.Default())
    override val navigationEvent: StateFlow<NavigationEvent> = _navigationEvent.asStateFlow()

    init {
        screenModelScope.launch {
        }
    }

    override fun sendEvent(event: ExampleEvent) {
        when(event){
            ExampleEvent.Back -> {
                _navigationEvent.value = NavigationEvent.Back()
            }
        }
    }
}
