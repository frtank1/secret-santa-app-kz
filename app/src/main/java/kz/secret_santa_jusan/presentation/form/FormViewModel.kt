package kz.secret_santa_jusan.presentation.form


import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kz.secret_santa_jusan.core.base.CoreBaseViewModel
import kz.secret_santa_jusan.core.storage.GlobalStorage
import kz.secret_santa_jusan.data.form.FromApiRepository
import kz.secret_santa_jusan.data.form.module.ContactModule
import kz.secret_santa_jusan.presentation.registration.RegistrationState
import trikita.log.Log


interface IFormViewModel {
    val state: StateFlow<FormState>
    val navigationEvent: StateFlow<NavigationEvent>
    fun sendEvent(event: FormEvent)
}

sealed class FormEvent(){
    object Back: FormEvent()
    object Next: FormEvent()
    class Init(val id: String): FormEvent()
    class EnterName(val text: String): FormEvent()
    class EnterNumber(val text: String): FormEvent()
    class EnterMail(val text: String): FormEvent()
}

sealed class NavigationEvent{
    private var handled: Boolean = false

    fun getValue(): NavigationEvent {
        if (handled) return Default()
        handled = true
        return this
    }

    class Default: NavigationEvent()
    object Back: NavigationEvent()
    class Next(val id:String): NavigationEvent()
}

sealed class FormState(val contact: ContactModule){
    class Default(contact: ContactModule): FormState(contact)

}

class FormViewModelPreview : IFormViewModel {
    override val state: StateFlow<FormState> = MutableStateFlow(FormState.Default(ContactModule("","",""))).asStateFlow()
    override val navigationEvent = MutableStateFlow(NavigationEvent.Default()).asStateFlow()
    override fun sendEvent(event: FormEvent) {}
}

class FormViewModel(
    private val repository: FromApiRepository
): CoreBaseViewModel(), IFormViewModel {

    private var _state = MutableStateFlow<FormState>(FormState.Default(ContactModule("","","")))
    override val state: StateFlow<FormState> = _state.asStateFlow()


    private val _navigationEvent = MutableStateFlow<NavigationEvent>(NavigationEvent.Default())
    override val navigationEvent: StateFlow<NavigationEvent> = _navigationEvent.asStateFlow()

    private var id:String = ""

    override fun sendEvent(event: FormEvent) {
        when(event){
             FormEvent.Back -> {
                _navigationEvent.value = NavigationEvent.Back
            }

            is FormEvent.EnterName -> {
                _state.value = FormState.Default(state.value.contact.copy(userName = event.text))
            }
            is FormEvent.EnterMail -> {
                _state.value = FormState.Default(state.value.contact.copy(email = event.text))
            }
            is FormEvent.EnterNumber -> {
                _state.value = FormState.Default(state.value.contact.copy(phoneNumber = event.text))
            }

            FormEvent.Next -> {
                screenModelScope.launch {
                    repository.putContsct(id,state.value.contact).apply {
                        if(isSuccessful) {
                            _navigationEvent.value = NavigationEvent.Next(id)
                        }
                    }
                }

            }

            is FormEvent.Init -> {
                id = event.id
                _state.value = FormState.Default(ContactModule("","",""))
            }
        }
    }
}
