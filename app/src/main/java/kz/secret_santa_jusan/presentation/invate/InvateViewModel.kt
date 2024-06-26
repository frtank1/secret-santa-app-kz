package kz.secret_santa_jusan.presentation.invate


import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kz.secret_santa_jusan.core.base.CoreBaseViewModel
import kz.secret_santa_jusan.core.storage.GlobalStorage
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

    object ReShuffle:InvateEvent()

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

    class GoToAddUser(val id:String):NavigationEvent()

    class GoToRegistration( val gameModel: GameModel?):NavigationEvent()
    class CreateCard(val id:String):NavigationEvent()

    class ShowWard(val gameModel: GameModel?):NavigationEvent()


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
    private var id:String? = null

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
                                  id= body?.gameId?:""
                                  _state.value = InvateState.UserScreen(body.gameId?:"")
                               /*   when(_statusCode){
                                      202 -> {
                                          sendEvent(InvateEvent.Init(body.gameId?:"",gameModel))
                                          _state.value = InvateState.ClosedScreen(body.gameId?:"","Secret Santa")

                                      }
                                      else -> {

                                      }
                                  }*/
                              }
                              else{

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
                if (GlobalStorage.access_token!=null){
                    _navigationEvent.value = NavigationEvent.CreateCard(id?:"")
                }else{
                    _navigationEvent.value = NavigationEvent.GoToRegistration(gameModel)
                }
            }
            is InvateEvent.GoToAddUser -> {
                _navigationEvent.value = NavigationEvent.GoToAddUser(gameModel?.id?:"")
            }
            is InvateEvent.ShowWard -> {
                _navigationEvent.value = NavigationEvent.ShowWard(gameModel)
            }

            InvateEvent.ReShuffle -> {
                screenModelScope.launch {
                    repository.reShufle(gameModel!!.id!!).apply {
                        if(isSuccessful) {
                            _state.value = InvateState.ClosedScreen(gameModel!!.id!!,gameModel?.name?:"")
                        }
                    }
                }
            }
        }
    }
}

fun stringParse(text: String): String {
    var result = ""
    val startIndex =
        text.indexOf("accept/")
    val endIndex = text.length
    if (startIndex != -1) {
        result = text.substring(startIndex+7, endIndex)
    } else {
        result
    }
    return result
}
