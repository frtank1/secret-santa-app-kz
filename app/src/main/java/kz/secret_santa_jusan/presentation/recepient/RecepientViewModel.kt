package kz.secret_santa_jusan.presentation.recepient


import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kz.secret_santa_jusan.core.base.CoreBaseViewModel
import kz.secret_santa_jusan.data.game.models.GameModel
import kz.secret_santa_jusan.data.recepient.RecepientApiRepository
import kz.secret_santa_jusan.data.recepient.models.PercepientModel

interface IRecepientViewModel {
    val state: StateFlow<RecepientState>
    val navigationEvent: StateFlow<NavigationEvent>
    fun sendEvent(event: RecepientEvent)
}

sealed class RecepientEvent {
    object Back : RecepientEvent()

    class Init(val gameModel: GameModel?) : RecepientEvent()

    object ShowGift:RecepientEvent()
}

sealed class NavigationEvent {
    private var handled: Boolean = false

    fun getValue(): NavigationEvent {
        if (handled) return Default()
        handled = true
        return this
    }

    class Default : NavigationEvent()
    class Back : NavigationEvent()
}

sealed class RecepientState {
    class Default(val name: String?, val userName: String?) : RecepientState()
    class Show(val name: String?, val percepient: PercepientModel) : RecepientState()


}

class RecepientViewModelPreview : IRecepientViewModel {
    override val state: StateFlow<RecepientState> =
        MutableStateFlow(RecepientState.Default("", "")).asStateFlow()
    override val navigationEvent = MutableStateFlow(NavigationEvent.Default()).asStateFlow()
    override fun sendEvent(event: RecepientEvent) {}
}

class RecepientViewModel(
    private val repository: RecepientApiRepository
) : CoreBaseViewModel(), IRecepientViewModel {

    private var _state = MutableStateFlow<RecepientState>(RecepientState.Default("", ""))
    override val state: StateFlow<RecepientState> = _state.asStateFlow()


    private val _navigationEvent = MutableStateFlow<NavigationEvent>(NavigationEvent.Default())
    override val navigationEvent: StateFlow<NavigationEvent> = _navigationEvent.asStateFlow()

    private var gameModel: GameModel? = null
  var percepientModel: PercepientModel? = null
    init {
        screenModelScope.launch {
        }
    }

    override fun sendEvent(event: RecepientEvent) {
        when (event) {
            RecepientEvent.Back -> {
                _navigationEvent.value = NavigationEvent.Back()
            }

            is RecepientEvent.Init -> {
                gameModel = event.gameModel
                screenModelScope.launch {
                    repository.getPercepient(event.gameModel?.id?:"").apply {
                        if (isSuccessful){
                            percepientModel = body
                        }
                    }
                }
                _state.value = RecepientState.Default(gameModel?.name?:"",percepientModel?.message?:"")

            }

            is RecepientEvent.ShowGift -> {
                percepientModel?.let {
                    _state.value = RecepientState.Show(gameModel?.name?:"",it)
                }

            }
        }
    }
}
