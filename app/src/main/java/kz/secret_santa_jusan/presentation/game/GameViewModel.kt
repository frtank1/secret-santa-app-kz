package kz.secret_santa_jusan.presentation.game


import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kz.secret_santa_jusan.core.base.CoreBaseViewModel
import kz.secret_santa_jusan.data.game.GameApiRepository
import kz.secret_santa_jusan.data.game.models.GameModel

interface IGameViewModel {
    val state: StateFlow<GameState>
    val navigationEvent: StateFlow<NavigationEvent>
    fun sendEvent(event: GameEvent)
}

sealed class GameEvent{
    object Back: GameEvent()

    object GoToCreate: GameEvent()

    class GoToCard(val id:Int): GameEvent()

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
    object GoToCreate: NavigationEvent()

    class GoToCard(val gameModel: GameModel): NavigationEvent()
}

sealed class GameState(val list: List<GameModel>){
    class Default(list: List<GameModel>): GameState(list)

    class Init( list: List<GameModel>): GameState(list)
}

class GameViewModelPreview : IGameViewModel {
    override val state: StateFlow<GameState> = MutableStateFlow(GameState.Default(emptyList())).asStateFlow()
    override val navigationEvent = MutableStateFlow(NavigationEvent.Default()).asStateFlow()
    override fun sendEvent(event: GameEvent) {}
}

class GameViewModel(
    private val repository: GameApiRepository
): CoreBaseViewModel(), IGameViewModel {

    private var _state = MutableStateFlow<GameState>(GameState.Default(emptyList()))
    override val state: StateFlow<GameState> = _state.asStateFlow()


    private val _navigationEvent = MutableStateFlow<NavigationEvent>(NavigationEvent.Default())
    override val navigationEvent: StateFlow<NavigationEvent> = _navigationEvent.asStateFlow()

    init {
        screenModelScope.launch {
            repository.myGames().apply {
                if(isSuccessful) {
                    if (!body.isNullOrEmpty())
                    _state.value = GameState.Init(body)
                    else
                        _state.value = GameState.Default(emptyList())
                }
            }
        }
    }

    override fun sendEvent(event: GameEvent) {
        when(event){
            GameEvent.Back -> {
                _navigationEvent.value = NavigationEvent.Back()
            }

            GameEvent.GoToCreate -> {
                _navigationEvent.value = NavigationEvent.GoToCreate
            }

            is GameEvent.GoToCard -> {
                _navigationEvent.value = NavigationEvent.GoToCard(state.value.list[event.id])
            }
        }
    }
}
