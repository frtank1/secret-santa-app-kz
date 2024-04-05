package kz.secret_santa_jusan.core.base

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle

import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import kz.secret_santa_jusan.core.navigation.ResultNavigation

import trikita.log.Log

abstract class CoreBaseScreen: Screen {

    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content(){

    }

    open fun onDispose(){}

    @Composable
    fun SubscribeError(viewModel: CoreBaseViewModel){
        val message = viewModel.showErrorMessageEvent.collectAsStateWithLifecycle().value
        if(!message.isNullOrEmpty()){
            Log.e("subscribeError", message)
            viewModel.cleareError()
        }
    }

    fun onBack(result:Any){
        ResultNavigation.setValue(result)
    }

    fun getResultScreen():Any?{
        return ResultNavigation.getValue()
    }
}