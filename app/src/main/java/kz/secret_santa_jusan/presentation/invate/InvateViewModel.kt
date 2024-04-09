package kz.secret_santa_jusan.presentation.invate


import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kz.secret_santa_jusan.core.base.CoreBaseViewModel
import kz.secret_santa_jusan.data.game.models.GameModel
import kz.secret_santa_jusan.data.invate.InvateApiRepository

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
    class Init(val link:String?, val gameModel: GameModel?): InvateEvent()
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

sealed class InvateState(val id:String){
    class Default( id:String):InvateState(id)
    class UserScreen(id:String): InvateState(id)
    class OrgScreen(id:String): InvateState(id)
    class ClosedScreen( id: String,val name:String): InvateState(id)
}

class InvateViewModelPreview : IInvateViewModel {
    override val state: StateFlow<InvateState> = MutableStateFlow(InvateState.Default("")).asStateFlow()
    override val navigationEvent = MutableStateFlow(NavigationEvent.Default()).asStateFlow()
    override fun sendEvent(event: InvateEvent) {}
}

class InvateViewModel(
    private val repository: InvateApiRepository
): CoreBaseViewModel(), IInvateViewModel {

    private var _state = MutableStateFlow<InvateState>(InvateState.Default(""))
    override val state: StateFlow<InvateState> = _state.asStateFlow()


    private val _navigationEvent = MutableStateFlow<NavigationEvent>(NavigationEvent.Default())
    override val navigationEvent: StateFlow<NavigationEvent> = _navigationEvent.asStateFlow()

    private var gameModel:GameModel? = null


    override fun sendEvent(event: InvateEvent) {

        when(event){
            InvateEvent.Back -> {
                _navigationEvent.value = NavigationEvent.Back()
            }

            is InvateEvent.Init -> {
                if(event.gameModel == null){
                  event.link?.let {
                      screenModelScope.launch {
                          val code = stringParse(event.link)
                          repository.acceptForInviteLink(code).apply {
                              if(isSuccessful) {
                                  when(_statusCode){
                                      202 -> {
                                          _state.value = InvateState.ClosedScreen(body.gameId?:"",body.gameId?:"")
                                      }
                                      else -> {
                                          _state.value = InvateState.ClosedScreen(body.gameId?:"",body.gameId?:"")
                                      }
                                  }
                              }
                          }
                      }
                  }
                }else{
                    gameModel = event.gameModel
                    when(event.gameModel.status){
                        "IN_PROCESS" -> {
                            if (event.gameModel.role == "ORGANISER"){
                                _state.value = InvateState.OrgScreen(gameModel!!.id!!)
                            }
                            else{
                                _state.value = InvateState.OrgScreen(gameModel!!.id!!)
                            }
                        }
                        "MATCHING_COMPLETED" ->{
                            _state.value = InvateState.ClosedScreen(gameModel!!.id!!,gameModel?.name?:"")
                        }
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

fun stringParse(text: String): String {
    var result = ""
    val startIndex =
        text.indexOf("code=")
    val endIndex = text.length
    if (startIndex != -1) {
        result = text.substring(startIndex, endIndex)
    } else {
        result
    }

    return result
}
