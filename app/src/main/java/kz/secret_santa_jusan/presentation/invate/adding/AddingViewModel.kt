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


    class Default(val id:String):AddingEvent()

    class GoToOwnAdd(val id:String):AddingEvent()

    class GoToLink(val id:String):AddingEvent()

    class GoToAdding(val id:String):AddingEvent()

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
    class GoToOwnAdd(val id:String):NavigationEvent()

    class GoToLink(val id:String):NavigationEvent()

    class GoToAdding(val id:String):NavigationEvent()

}

sealed class AddingState{
    class Default(val id: String): AddingState()
}

class AddingViewModelPreview : IAddingViewModel {
    override val state: StateFlow<AddingState> = MutableStateFlow(AddingState.Default("")).asStateFlow()
    override val navigationEvent = MutableStateFlow(NavigationEvent.Default()).asStateFlow()
    override fun sendEvent(event: AddingEvent) {}
}

class AddingViewModel(
): CoreBaseViewModel(), IAddingViewModel {

    private var _state = MutableStateFlow<AddingState>(AddingState.Default(""))
    override val state: StateFlow<AddingState> = _state.asStateFlow()


    private val _navigationEvent = MutableStateFlow<NavigationEvent>(NavigationEvent.Default())
    override val navigationEvent: StateFlow<NavigationEvent> = _navigationEvent.asStateFlow()

    private val id:String = ""

    init {
        screenModelScope.launch {
        }
    }

    override fun sendEvent(event: AddingEvent) {
        when(event){
            AddingEvent.Back -> {
                _navigationEvent.value = NavigationEvent.Back()
            }

            is AddingEvent.GoToAdding -> {
                _navigationEvent.value = NavigationEvent.GoToAdding(event.id)
            }
            is AddingEvent.GoToLink -> {
                _navigationEvent.value = NavigationEvent.GoToLink(event.id)
            }
            is AddingEvent.GoToOwnAdd -> {
                _navigationEvent.value = NavigationEvent.GoToOwnAdd(event.id)
            }

            is AddingEvent.Default -> {
                _state.value = AddingState.Default(event.id)
            }
        }
    }
}
