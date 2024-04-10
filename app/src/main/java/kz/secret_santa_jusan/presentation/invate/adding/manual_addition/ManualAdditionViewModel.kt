package kz.secret_santa_jusan.presentation.invate.adding.manual_addition


import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kz.secret_santa_jusan.core.base.CoreBaseViewModel
import kz.secret_santa_jusan.data.invate.manual.ManualAddApiRepository
import kz.secret_santa_jusan.data.invate.manual.model.UserNameModel

interface IManualAdditionViewModel {
    val state: StateFlow<ManualAdditionState>
    val navigationEvent: StateFlow<NavigationEvent>
    fun sendEvent(event: ManualAdditionEvent)
}

sealed class ManualAdditionEvent {
    object Back : ManualAdditionEvent()

    class Init(val id: String) : ManualAdditionEvent()
    class EnterUserName(val id: Int, val name: String) : ManualAdditionEvent()

    class EnterUserEmail(val id: Int, val email: String) : ManualAdditionEvent()
    class SendLink(val id: Int, val email: String) : ManualAdditionEvent()
    object addNewUSer : ManualAdditionEvent()
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

sealed class ManualAdditionState(val list: List<UserNameModel>) {
    class Default(list: List<UserNameModel>) : ManualAdditionState(list)

}

class ManualAdditionViewModelPreview : IManualAdditionViewModel {
    override val state: StateFlow<ManualAdditionState> = MutableStateFlow(
        ManualAdditionState.Default(
            emptyList()
        )
    ).asStateFlow()
    override val navigationEvent = MutableStateFlow(NavigationEvent.Default()).asStateFlow()
    override fun sendEvent(event: ManualAdditionEvent) {}
}

class ManualAdditionViewModel(
    private val repository: ManualAddApiRepository
) : CoreBaseViewModel(), IManualAdditionViewModel {

    private var _state =
        MutableStateFlow<ManualAdditionState>(ManualAdditionState.Default(emptyList()))
    override val state: StateFlow<ManualAdditionState> = _state.asStateFlow()


    private val _navigationEvent = MutableStateFlow<NavigationEvent>(NavigationEvent.Default())
    override val navigationEvent: StateFlow<NavigationEvent> = _navigationEvent.asStateFlow()

    private var id: String = ""

    override fun sendEvent(event: ManualAdditionEvent) {
        when (event) {
            ManualAdditionEvent.Back -> {
                _navigationEvent.value = NavigationEvent.Back()
            }

            is ManualAdditionEvent.EnterUserEmail -> {
                val updatedList = state.value.list.mapIndexed { index, user ->
                    if (index == event.id) {
                        user.copy(email = event.email)
                    } else {
                        user
                    }
                }
                _state.value = ManualAdditionState.Default(updatedList)
            }

            is ManualAdditionEvent.EnterUserName -> {
                val updatedList = state.value.list.mapIndexed { index, user ->
                    if (index == event.id) {
                        user.copy(name = event.name)
                    } else {
                        user
                    }
                }
                _state.value = ManualAdditionState.Default(updatedList)
            }

            is ManualAdditionEvent.SendLink -> {
                screenModelScope.launch {
                    if (!_state.value.list.isNullOrEmpty()) {
                        repository.sendUser(id, state.value.list)
                    }
                }
            }

            is ManualAdditionEvent.Init -> {
                id = event.id
                _state.value = ManualAdditionState.Default(listOf(UserNameModel("", "")))
            }

            ManualAdditionEvent.addNewUSer -> {
                val user = UserNameModel("", "")
                val updatedList = state.value.list + user
                _state.value = ManualAdditionState.Default(updatedList)
            }
        }
    }
}

