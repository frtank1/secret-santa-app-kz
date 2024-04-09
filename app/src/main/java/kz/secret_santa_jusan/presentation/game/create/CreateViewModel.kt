package kz.secret_santa_jusan.presentation.game.create


import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kz.secret_santa_jusan.core.base.CoreBaseViewModel
import kz.secret_santa_jusan.data.game.create.CreateApiRepository
import kz.secret_santa_jusan.data.game.create.model.RequestGameModel

data class GameSettings(
    val name: String? = "",
    val maxPrice: Int? = 1,
    val uniqueIdentifier: String? = "",
    val priceLimitChecked: Boolean = false,
    val showSum: Boolean = false,
    val erroSum: Boolean = false
)

interface ICreateViewModel {
    val state: StateFlow<CreateState>
    val navigationEvent: StateFlow<NavigationEvent>
    fun sendEvent(event: CreateEvent)
}

sealed class CreateEvent {
    object Back : CreateEvent()

    class EnterName(val text: String) : CreateEvent()
    class EnterSum(val text: String) : CreateEvent()
    class ShowSum(val boolean: Boolean) : CreateEvent()
    object CreateGame : CreateEvent()
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

sealed class CreateState(val createData: GameSettings) {
    class Default(createData: GameSettings) : CreateState(createData)
}

class CreateViewModelPreview : ICreateViewModel {
    override val state: StateFlow<CreateState> =
        MutableStateFlow(CreateState.Default(GameSettings())).asStateFlow()
    override val navigationEvent = MutableStateFlow(NavigationEvent.Default()).asStateFlow()
    override fun sendEvent(event: CreateEvent) {}
}

class CreateViewModel(
    private val repository: CreateApiRepository
) : CoreBaseViewModel(), ICreateViewModel {

    private var _state = MutableStateFlow<CreateState>(CreateState.Default(GameSettings()))
    override val state: StateFlow<CreateState> = _state.asStateFlow()


    private val _navigationEvent = MutableStateFlow<NavigationEvent>(NavigationEvent.Default())
    override val navigationEvent: StateFlow<NavigationEvent> = _navigationEvent.asStateFlow()

    init {
        screenModelScope.launch {
        }
    }

    override fun sendEvent(event: CreateEvent) {
        when (event) {
            CreateEvent.Back -> {
                _navigationEvent.value = NavigationEvent.Back()
            }

            CreateEvent.CreateGame -> {
                screenModelScope.launch {
                    val requestGameModel = RequestGameModel(
                        name = state.value.createData.name,
                        maxPrice = state.value.createData.maxPrice,
                        uniqueIdentifier = "",
                        priceLimitChecked = state.value.createData.priceLimitChecked
                    )
                    repository.create(requestGameModel).apply {
                        if (isSuccessful) {
                            body.id
                        }
                    }
                }
            }

            is CreateEvent.EnterName -> {
                _state.value = CreateState.Default(state.value.createData.copy(name = event.text))
            }

            is CreateEvent.EnterSum -> {
                _state.value =
                    CreateState.Default(state.value.createData.copy(maxPrice = event.text.toInt()))
            }

            is CreateEvent.ShowSum -> {
                if (event.boolean) {
                    _state.value = CreateState.Default(state.value.createData.copy(showSum = event.boolean, priceLimitChecked = true))
                } else {
                    _state.value = CreateState.Default(
                        state.value.createData.copy(
                            showSum = event.boolean,
                            maxPrice = 1,
                            priceLimitChecked =false
                        )
                    )
                }
            }
        }
    }
}
