package kz.secret_santa_jusan.presentation.auth

import android.os.Parcelable
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.parcelize.Parcelize
import kz.secret_santa_jusan.R
import kz.secret_santa_jusan.core.base.CoreBaseScreen
import kz.secret_santa_jusan.core.views.AOButton
import kz.secret_santa_jusan.core.views.EditText
import kz.secret_santa_jusan.core.views.SsText
import kz.secret_santa_jusan.presentation.registration.AgreeText
import kz.secret_santa_jusan.presentation.registration.EnterText
import kz.secret_santa_jusan.presentation.registration.IRegistrationViewModel
import kz.secret_santa_jusan.presentation.registration.RegistrationEvent
import kz.secret_santa_jusan.presentation.registration.spliterOr
import kz.secret_santa_jusan.ui.theme.BrightOrange
import kz.secret_santa_jusan.ui.theme.DarkGray
import kz.secret_santa_jusan.ui.theme.Gray
import kz.secret_santa_jusan.ui.theme.LightGrey
import kz.secret_santa_jusan.ui.theme.interFamily

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
            Button(
                onClick = {
            }) {
                Text("OpenExample")
            }
        }
    }
}

@Composable
fun AuthMenu(viewModel: IAuthViewModel) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    Column {
        SsText(
            text = stringResource(id = R.string.Регистрация),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            fontSize = 40.sp,
        )
        EditText(
            value = state.regForm.login ?: "-",
            onValueChange = { login ->
                viewModel.sendEvent()
            },
            enabled = true,
            isError = false,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 73.dp),
            label = stringResource(R.string.Ваше_Имя)
        )
        EditText(
            value = state.regForm.password ?: "-",
            onValueChange = { pasword ->
                viewModel.sendEvent()
            },
            enabled = true,
            isError = false,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            label = stringResource(R.string.пароль)
        )
        Text(
            modifier = Modifier
                .padding(horizontal = 2.dp)
                .clickable {  },
            text = stringResource(id = R.string.Забыли_пароль),
            color = Gray,
            fontFamily = interFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 10.sp,
            style = TextStyle(textDecoration = TextDecoration.Underline)
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 126.dp)
                .padding(horizontal = 25.dp),
            colors = ButtonDefaults.buttonColors(BrightOrange),
            onClick = {
                viewModel.sendEvent(RegistrationEvent.ClickEnter)
            }) {
            Text(
                stringResource(id = R.string.Зарегистрироваться),
                fontFamily = interFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
        }

    }
}
