package kz.secret_santa_jusan.presentation.invate


import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kz.secret_santa_jusan.core.base.CoreBaseViewModel
import kz.secret_santa_jusan.data.registration.RegisterApiRepository
import trikita.log.Log

interface IInvateViewModel {
    val state: StateFlow<InvateState>
    val navigationEvent: StateFlow<NavigationEvent>
    fun sendEvent(event: InvateEvent)
}
enum class TypeLink(
){
    USER,ORGANIZATOR,CREATED
}

sealed class InvateEvent{
    object Back: InvateEvent()
    class Init(val typeLink:TypeLink, val id:String): InvateEvent()
    class ContactWhitOrg:InvateEvent()
    class GoToAddUser:InvateEvent()

    class CreateCard:InvateEvent()

    class ShowWard:InvateEvent()

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

    class ContactWhitOrg:NavigationEvent()

    class GoToAddUser:NavigationEvent()

    class CreateCard:NavigationEvent()

    class ShowWard:NavigationEvent()


}

sealed class InvateState{
    object Default:InvateState()
    object UserScreen: InvateState()
    class OrgScreen(val nameGame:String): InvateState()
    object CreatedScreen: InvateState()
}

class InvateViewModelPreview : IInvateViewModel {
    override val state: StateFlow<InvateState> = MutableStateFlow(InvateState.Default).asStateFlow()
    override val navigationEvent = MutableStateFlow(NavigationEvent.Default()).asStateFlow()
    override fun sendEvent(event: InvateEvent) {}
}

class InvateViewModel(
    private val repository: RegisterApiRepository
): CoreBaseViewModel(), IInvateViewModel {

    private var _state = MutableStateFlow<InvateState>(InvateState.Default)
    override val state: StateFlow<InvateState> = _state.asStateFlow()


    private val _navigationEvent = MutableStateFlow<NavigationEvent>(NavigationEvent.Default())
    override val navigationEvent: StateFlow<NavigationEvent> = _navigationEvent.asStateFlow()

    private var id:String = ""

    override fun sendEvent(event: InvateEvent) {

        when(event){
            InvateEvent.Back -> {
                _navigationEvent.value = NavigationEvent.Back()
            }

            is InvateEvent.Init -> {
                id = event.id
                when(event.typeLink){
                    TypeLink.USER -> {
                        _state.value = InvateState.UserScreen
                    }
                    TypeLink.ORGANIZATOR -> {
                        screenModelScope.launch {

                        }
                        _state.value = InvateState.OrgScreen()
                    }
                    TypeLink.CREATED -> {
                        _state.value = InvateState.CreatedScreen
                    }
                }
            }

            is InvateEvent.ContactWhitOrg -> {
            }
            is InvateEvent.CreateCard -> {
            }
            is InvateEvent.GoToAddUser -> {
            }
            is InvateEvent.ShowWard -> {
            }
        }
    }
}
