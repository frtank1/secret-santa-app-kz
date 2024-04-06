package kz.secret_santa_jusan.presentation.game


import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kz.secret_santa_jusan.core.base.CoreBaseViewModel

interface IGameViewModel {
    val state: StateFlow<GameState>
    val navigationEvent: StateFlow<NavigationEvent>
    fun sendEvent(event: GameEvent)
}

sealed class GameEvent{
    object Back: GameEvent()
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

sealed class GameState{
    object Default: GameState()
}

class GameViewModelPreview : IGameViewModel {
    override val state: StateFlow<GameState> = MutableStateFlow(GameState.Default).asStateFlow()
    override val navigationEvent = MutableStateFlow(NavigationEvent.Default()).asStateFlow()
    override fun sendEvent(event: GameEvent) {}
}

class GameViewModel(
): CoreBaseViewModel(), IGameViewModel {

    private var _state = MutableStateFlow<GameState>(GameState.Default)
    override val state: StateFlow<GameState> = _state.asStateFlow()


    private val _navigationEvent = MutableStateFlow<NavigationEvent>(NavigationEvent.Default())
    override val navigationEvent: StateFlow<NavigationEvent> = _navigationEvent.asStateFlow()

    init {
        screenModelScope.launch {
        }
    }

    override fun sendEvent(event: GameEvent) {
        when(event){
            GameEvent.Back -> {
                _navigationEvent.value = NavigationEvent.Back()
            }
        }
    }
}
