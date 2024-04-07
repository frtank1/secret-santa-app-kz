package kz.secret_santa_jusan.presentation.auth.pass_recovery

import android.os.Parcelable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
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
import kz.secret_santa_jusan.presentation.registration.RegistrationEvent
import kz.secret_santa_jusan.ui.theme.BrightOrange
import kz.secret_santa_jusan.ui.theme.DarkGray
import kz.secret_santa_jusan.ui.theme.Gray
import kz.secret_santa_jusan.ui.theme.PaleBlue
import kz.secret_santa_jusan.ui.theme.interFamily

@Parcelize
class PassRecoveryScreen : CoreBaseScreen(), Parcelable {

    @Composable
    override fun Content() {
        val viewModel = getScreenModel<PassRecoveryViewModel>()
        val navigator = LocalNavigator.currentOrThrow
        val navigationEvent =
            viewModel.navigationEvent.collectAsStateWithLifecycle().value.getValue()
        when (navigationEvent) {
            is NavigationEvent.Default -> {}
            is NavigationEvent.Back -> navigator.pop()
            //is NavigationEvent.AuthRouter -> navigator.push(ScreenRegistry.get(AuthRouter.ProfileScreen()))
        }
        SubscribeError(viewModel)
        PassRecoveryContent(viewModel = viewModel)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PassRecoveryContentPreview() {
    PassRecoveryContent(PassRecoveryViewModelPreview())
}


@Composable
fun PassRecoveryContent(viewModel: IPassRecoveryViewModel) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    Column {
        TitleBar(onClickBack = {
            viewModel.sendEvent(PassRecoveryEvent.Back)
        })
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = PaleBlue)
                .padding(horizontal = 36.dp),
            horizontalAlignment = Alignment.CenterHorizontally
            //.verticalScroll(rememberScrollState())
        ) {
            when (state) {
                is PassRecoveryState.Default -> {
                    RecoveryMenu(viewModel)
                }

                is PassRecoveryState.MessegeSended -> {messegeSended()}
            }
        }
    }
}


@Composable
fun RecoveryMenu(viewModel: IPassRecoveryViewModel) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    Column {
        SsText(
            modifier = Modifier
                .fillMaxWidth()
            .padding(top = 79.dp),
            text = stringResource(id = R.string.Восстановление_доступа),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            fontSize = 22.sp,
        )
        EditText(
            value = state.email ?: "-",
            onValueChange = { mail ->
                viewModel.sendEvent(PassRecoveryEvent.EnterEmail(mail))
            },
            enabled = true,
            isError = false,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 100.dp),
            label = stringResource(R.string.Ваш_mail)
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { }
                .padding(14.dp),
            textAlign = TextAlign.Center,
            text = stringResource(id = R.string.Мы_отправим_ссылку_с_временным_паролем),
            color = Gray,
            fontFamily = interFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 8.sp,
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 126.dp)
                .padding(horizontal = 25.dp),
            colors = ButtonDefaults.buttonColors(BrightOrange),
            onClick = {
                viewModel.sendEvent(PassRecoveryEvent.ClickEnter)
            }) {
            Text(
                stringResource(id = R.string.Восстановить),
                fontFamily = interFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
        }
        TextWithUnderline(
            textFirst = stringResource(id = R.string.Продолжая_Вы_даете_согласие_на),
            textSecond = stringResource(id = R.string.обработку_персональных_данных)
        )
    }
}

@Composable
fun messegeSended(){
    SsText(
        modifier = Modifier
            .padding(top = 25.dp)
            .fillMaxWidth(),
        text = stringResource(id = R.string.Письмо_отправлено),
        color = DarkGray,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        fontSize = 40.sp,
    )
    Column {
        Image(
            modifier = Modifier
                .padding(top = 42.dp)
                .align(Alignment.CenterHorizontally),
            painter = painterResource(id = R.drawable.santa02),
            contentDescription = null,
        )
        TextWithUnderline(
            textFirst = stringResource(id = R.string.Письмо_отправлено_v),
            textSecond = stringResource(id = R.string.Не_пришло ),
            color = BrightOrange
        )
    }
}