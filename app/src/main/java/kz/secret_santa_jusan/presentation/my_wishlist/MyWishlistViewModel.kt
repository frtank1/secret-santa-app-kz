package kz.secret_santa_jusan.presentation.my_wishlist

import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kz.secret_santa_jusan.core.base.CoreBaseViewModel
import kz.secret_santa_jusan.core.storage.GlobalStorage
import kz.secret_santa_jusan.data.wishlist.WishlistApiRepository
import kz.secret_santa_jusan.data.wishlist.models.CreateWishlistModel
import kz.secret_santa_jusan.presentation.profile.RessetData
import trikita.log.Log

class MyWishlistViewModel(
    private val repository: WishlistApiRepository
) : CoreBaseViewModel(), IMyWishlistViewModel {
    private val _state = MutableStateFlow(MyWishlistState.Default(emptyList()))
    override val state: StateFlow<MyWishlistState> = _state.asStateFlow()

    private val _navigationEvent = MutableStateFlow<NavigationEvent>(NavigationEvent.Default())
    override val navigationEvent: StateFlow<NavigationEvent> = _navigationEvent.asStateFlow()

    private var id:String = ""
    override fun sendEvent(event: MyWishlistEvent) {
        when (event) {
            is MyWishlistEvent.Init -> {
                id = event.id
            }

            is MyWishlistEvent.Back -> {
                _navigationEvent.value = NavigationEvent.Back()
            }

            is MyWishlistEvent.CreateMyWishlist -> {
                createWishlist()
            }

            MyWishlistEvent.AddAnotherGift -> {
                val update = state.value.gifts +""
                _state.value = MyWishlistState.Default(update)
                }

            is MyWishlistEvent.EnterGift -> {
                val update = state.value.gifts.mapIndexed { index, s ->
                    if (index == event.index) {
                       event.text
                    } else {
                        s
                    }
                }
                _state.value = MyWishlistState.Default(update)
            }
        }
    }


    private fun createWishlist() = with(state.value){
        screenModelScope.launch {
            repository.createWishlist(CreateWishlistModel(
                gameId = id,
                gifts = gifts
            )).apply {
                if(isSuccessful) {
                    Log.d("ok", "ok")
                }
            }
        }
    }
}

interface IMyWishlistViewModel {
    val state: StateFlow<MyWishlistState>
    val navigationEvent: StateFlow<NavigationEvent>
    fun sendEvent(event: MyWishlistEvent)
}

class MyWishlistViewModelPreview : IMyWishlistViewModel {
    override val state: StateFlow<MyWishlistState> = MutableStateFlow(
        MyWishlistState.Default(emptyList())
    ).asStateFlow()
    override val navigationEvent = MutableStateFlow(NavigationEvent.Default()).asStateFlow()
    override fun sendEvent(event: MyWishlistEvent) {}
}


sealed class MyWishlistEvent {
    class Init(val id:String) : MyWishlistEvent()
    object Back : MyWishlistEvent()
    object CreateMyWishlist : MyWishlistEvent()
    object AddAnotherGift : MyWishlistEvent()

    class EnterGift(val text: String, val index: Int) : MyWishlistEvent()
}


sealed class MyWishlistState(val gifts: List<String>){
    class Default(  gifts: List<String>): MyWishlistState(gifts)

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
