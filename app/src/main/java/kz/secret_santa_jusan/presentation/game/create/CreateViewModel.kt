package kz.secret_santa_jusan.presentation.game.create


import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kz.secret_santa_jusan.core.base.CoreBaseViewModel
import kz.secret_santa_jusan.data.game.GameApiRepository
import kz.secret_santa_jusan.data.game.create.CreateApiRepository
import kz.secret_santa_jusan.presentation.profile.ProfileEvent
import kz.secret_santa_jusan.presentation.profile.ProfileState

data class CreateData(
    val nameGame:String? = "",
    val showSum:Boolean = false,
    val sum: String? = "",
)
interface ICreateViewModel {
    val state: StateFlow<CreateState>
    val navigationEvent: StateFlow<NavigationEvent>
    fun sendEvent(event: CreateEvent)
}

sealed class CreateEvent{
    object Back: CreateEvent()

    class EnterName(val text: String): CreateEvent()
    class EnterSum(val text: String): CreateEvent()
    class showSum(val boolean: Boolean): CreateEvent()
    object CreateGame: CreateEvent()
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

sealed class CreateState(val createData:CreateData){
    class Default(createData:CreateData): CreateState(createData)
}

class CreateViewModelPreview : ICreateViewModel {
    override val state: StateFlow<CreateState> = MutableStateFlow(CreateState.Default(CreateData())).asStateFlow()
    override val navigationEvent = MutableStateFlow(NavigationEvent.Default()).asStateFlow()
    override fun sendEvent(event: CreateEvent) {}
}

class CreateViewModel(
    private val repository: CreateApiRepository
): CoreBaseViewModel(), ICreateViewModel {

    private var _state = MutableStateFlow<CreateState>(CreateState.Default(CreateData()))
    override val state: StateFlow<CreateState> = _state.asStateFlow()


    private val _navigationEvent = MutableStateFlow<NavigationEvent>(NavigationEvent.Default())
    override val navigationEvent: StateFlow<NavigationEvent> = _navigationEvent.asStateFlow()

    init {
        screenModelScope.launch {
        }
    }

    override fun sendEvent(event: CreateEvent) {
        when(event){
            CreateEvent.Back -> {
                _navigationEvent.value = NavigationEvent.Back()
            }

            CreateEvent.CreateGame -> {

            }
            is CreateEvent.EnterName -> {
                _state.value = CreateState.Default(state.value.createData.copy(nameGame  = event.text))
            }
            is CreateEvent.EnterSum -> {
                _state.value = CreateState.Default(state.value.createData.copy(sum  = event.text))
            }
            is CreateEvent.showSum -> {
                if (event.boolean){
                    _state.value = CreateState.Default(state.value.createData.copy(showSum  = event.boolean))
                }else{
                    _state.value = CreateState.Default(state.value.createData.copy(showSum  = event.boolean, sum = ""))
                }
            }
        }
    }
}
