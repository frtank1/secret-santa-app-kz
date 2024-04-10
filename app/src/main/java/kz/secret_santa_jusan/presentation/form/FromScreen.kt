package kz.secret_santa_jusan.presentation.form

import android.os.Parcelable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.parcelize.Parcelize
import kz.secret_santa_jusan.R
import kz.secret_santa_jusan.core.base.CoreBaseScreen
import kz.secret_santa_jusan.core.views.EditText
import kz.secret_santa_jusan.core.views.SsText
import kz.secret_santa_jusan.core.views.TextWithUnderline
import kz.secret_santa_jusan.core.views.TitleBar
import kz.secret_santa_jusan.presentation.form.my_wishlist.MyWishlistScreen
import kz.secret_santa_jusan.presentation.registration.IRegistrationViewModel
import kz.secret_santa_jusan.presentation.registration.RegistrationEvent
import kz.secret_santa_jusan.presentation.registration.spliterOr
import kz.secret_santa_jusan.ui.theme.BrightOrange
import kz.secret_santa_jusan.ui.theme.DarkGray
import kz.secret_santa_jusan.ui.theme.LightGrey
import kz.secret_santa_jusan.ui.theme.PaleBlue
import kz.secret_santa_jusan.ui.theme.interFamily

@Parcelize
class FormScreen(val id:String) : CoreBaseScreen(), Parcelable {

    @Composable
    override fun Content() {
        val viewModel = getScreenModel<FormViewModel>()
        val navigator = LocalNavigator.currentOrThrow
        val navigationEvent =
            viewModel.navigationEvent.collectAsStateWithLifecycle().value.getValue()
        when (navigationEvent) {
            is NavigationEvent.Default -> {}
             NavigationEvent.Back -> navigator.pop()
            is NavigationEvent.Next -> {
                navigator.replace(
                    MyWishlistScreen(navigationEvent.id)
                )
            }

        }
        SubscribeError(viewModel)
        FormContent(viewModel = viewModel)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun FormContentPreview() {
    FormContent(FormViewModelPreview())
}

@Composable
fun FormContent(viewModel: IFormViewModel) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    Column {
        TitleBar(onClickBack = {
            viewModel.sendEvent(FormEvent.Back)
        })
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = PaleBlue)
                .padding(horizontal = 36.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
            //.verticalScroll(rememberScrollState())
        ) {
            when (state) {
                is FormState.Default -> {
                    registrationMenu(viewModel)
                }
            }
        }
    }
}

@Composable
fun registrationMenu(viewModel: IFormViewModel) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    Column {
        SsText(
            text = stringResource(id = R.string.Контактные_Данные),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            fontSize = 29.sp,
        )
        EditText(
            value = state.contact.userName ?: "-",
            onValueChange = { userName ->
                viewModel.sendEvent(FormEvent.EnterName(userName))
            },
            enabled = true,
            isError = false,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 79.dp),
            label = stringResource(R.string.Ваше_Имя)
        )
        EditText(
            value = state.contact.email ?: "-",
            onValueChange = { email ->
                viewModel.sendEvent(FormEvent.EnterMail(email))
            },
            enabled = true,
            isError = false,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            label = stringResource(R.string.Ваш_mail)
        )
        EditText(
            value = state.contact.phoneNumber ?: "-",
            onValueChange = { pasword ->
                viewModel.sendEvent(FormEvent.EnterNumber(pasword))
            },
            enabled = true,
            isError = false,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 25.dp),
            label = stringResource(R.string.Номер_телефона)
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 126.dp)
                .padding(horizontal = 25.dp),
            colors = ButtonDefaults.buttonColors(BrightOrange),
            onClick = {
                viewModel.sendEvent(FormEvent.Next)
            }) {
            Text(
                stringResource(id = R.string.Далее),
                fontFamily = interFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
        }
        TextWithUnderline(
            textFirst = stringResource(id = R.string.Уже_есть_аккаунт),
            textSecond = stringResource(id = R.string.Войти),
            onClick = {
                viewModel.sendEvent(FormEvent.Next)
            }
        )
    }
}
