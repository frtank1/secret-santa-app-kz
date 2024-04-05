package kz.secret_santa_jusan.core.base

import cafe.adriel.voyager.core.model.ScreenModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class CoreBaseViewModel : ScreenModel {

    private val _showErrorMessageResEvent = MutableStateFlow<Int?>(null)
    val showErrorMessageResEvent: StateFlow<Int?> = _showErrorMessageResEvent

    private val _showErrorMessageEvent = MutableStateFlow<String?>(null)
    val showErrorMessageEvent: StateFlow<String?> = _showErrorMessageEvent

    fun cleareError(){
        _showErrorMessageResEvent.value = null
        _showErrorMessageEvent.value = null
    }

    fun createErrorEvent(error: String?){
        _showErrorMessageEvent.value = error
        /*if(error == null) return false
        return when(error.status){
            CustomErrorCode.INTERNET_DISMISS -> {
                _showErrorMessageResEvent.value = R.string.error_check_internet
                return true
            }
            CustomErrorCode.UNKNOWN_ERROR -> {
                _showErrorMessageResEvent.value = R.string.error_unknown
                return true
            }
            CustomErrorCode.TIMEOUT_ERROR -> {
                _showErrorMessageResEvent.value = R.string.error_timeout
                return true
            }
            CustomErrorCode.AUTH_ERROR -> {
                _showErrorMessageResEvent.value = R.string.error_auth
                return true
            }
            CustomErrorCode.BAD_GETAWAY_ERROR -> {
                _showErrorMessageResEvent.value = R.string.error_badgetaway
                return true
            }
            else -> {
                if(error.message != null) {
                    _showErrorMessageEvent.value = error.message
                    return true
                } else if(error.customMessage != null) {
                    _showErrorMessageEvent.value = error.customMessage
                    return true
                }else return false
            }
        }*/
    }
}