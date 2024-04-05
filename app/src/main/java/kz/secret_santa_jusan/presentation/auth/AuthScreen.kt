package kz.secret_santa_jusan.presentation.auth

import android.os.Parcelable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.parcelize.Parcelize
import kz.secret_santa_jusan.core.base.CoreBaseScreen

@Parcelize
class AuthScreen : CoreBaseScreen(), Parcelable {

    @Composable
    override fun Content() {
        val viewModel = getScreenModel<AuthViewModel>()
        val navigator = LocalNavigator.currentOrThrow
        val navigationEvent = viewModel.navigationEvent.collectAsStateWithLifecycle().value.getValue()
        when(navigationEvent){
            is NavigationEvent.Default -> {}
            is NavigationEvent.Back -> navigator.pop()
            //is NavigationEvent.AuthRouter -> navigator.push(ScreenRegistry.get(AuthRouter.ProfileScreen()))
        }
        SubscribeError(viewModel)
        ExampleContent(viewModel = viewModel)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ExampleContentPreview() {
    ExampleContent(AuthViewModelPreview())
}


@Composable
fun ExampleContent(viewModel: IAuthViewModel) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    when (state) {
        is AuthState.Default -> {
            Text("ExampleState Default")
        }
    }
}