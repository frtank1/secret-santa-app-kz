package kz.secret_santa_jusan.presentation.profile

import android.os.Parcelable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
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
import kz.secret_santa_jusan.core.views.EditTextPassword
import kz.secret_santa_jusan.core.views.ProfileInfoCadr
import kz.secret_santa_jusan.core.views.SsText
import kz.secret_santa_jusan.core.views.TitleBar
import kz.secret_santa_jusan.ui.theme.BrightOrange
import kz.secret_santa_jusan.ui.theme.DarkGray
import kz.secret_santa_jusan.ui.theme.Gray
import kz.secret_santa_jusan.ui.theme.LightGrey
import kz.secret_santa_jusan.ui.theme.PaleBlue
import kz.secret_santa_jusan.ui.theme.Red
import kz.secret_santa_jusan.ui.theme.White
import kz.secret_santa_jusan.ui.theme.interFamily

@Parcelize
class ProfileScreen : CoreBaseScreen(), Parcelable {

    @Composable
    override fun Content() {
        ShowBottomBar()
        val viewModel = getScreenModel<ProfileViewModel>()
        val navigator = LocalNavigator.currentOrThrow
        val navigationEvent =
            viewModel.navigationEvent.collectAsStateWithLifecycle().value.getValue()
        when (navigationEvent) {
            is NavigationEvent.Default -> {}
            is NavigationEvent.Back -> navigator.pop()
        }
        SubscribeError(viewModel)
        ProfileContent(viewModel = viewModel)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileContentPreview() {
    ProfileContent(ProfileViewModelPreview())
}


@Composable
fun ProfileContent(viewModel: IProfileViewModel) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    Column {
        TitleBar(onClickBack = {
            viewModel.sendEvent(ProfileEvent.Back)
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
                is ProfileState.Default -> {
                    ProfileMenu(viewModel)
                }
            }
        }
    }
}


@Composable
fun ProfileMenu(viewModel: IProfileViewModel) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    Column {
        ProfileInfoCadr(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 47.dp),
            name = state.ressetData.regModel.login,
            email = state.ressetData.regModel.email
        )
        SsText(
            modifier = Modifier
                .padding(top = 18.dp)
                .fillMaxWidth(),
            text = stringResource(id = R.string.Личные_Данные),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
        )
        EditText(
            value = state.ressetData.name ?: "-",
            onValueChange = { login ->
                viewModel.sendEvent(ProfileEvent.EnterLogin(login))
            },
            enabled = true,
            isError = false,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp),
            label = stringResource(R.string.Ваше_Имя)
        )
        EditText(
            value = state.ressetData.email ?: "-",
            onValueChange = { email ->
                viewModel.sendEvent(ProfileEvent.EnterMail(email))
            },
            enabled = true,
            isError = false,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 25.dp),
            label = stringResource(R.string.Ваш_mail)
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { viewModel.sendEvent(ProfileEvent.SaveProfile) }
                .padding(14.dp),
            textAlign = TextAlign.Center,
            text = stringResource(id = R.string.Сохранить),
            color = Gray,
            fontFamily = interFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            style = TextStyle(textDecoration = TextDecoration.Underline)
        )

        SsText(
            modifier = Modifier
                .padding(top = 18.dp)
                .fillMaxWidth(),
            text = stringResource(id = R.string.Пароль),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
        )
        EditTextPassword(
            value = state.ressetData.current ?: "-",
            onValueChange = { password ->
                viewModel.sendEvent(ProfileEvent.EnterCurrent(password))
            },
            enabled = true,
            isError = state.ressetData.errorPassword,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp),
            label = stringResource(R.string.Действующий_пароль)
        )
        EditTextPassword(
            value = state.ressetData.newPasword ?: "-",
            onValueChange = { password ->
                    viewModel.sendEvent(ProfileEvent.EnterPassword(password))
            },
            enabled = true,
            isError = state.ressetData.errorNewPassword,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 25.dp),
            label = stringResource(R.string.Новый_Пароль)
        )
        EditTextPassword(
            value = state.ressetData.repeatPasword ?: "-",
            onValueChange = { password ->
                viewModel.sendEvent(ProfileEvent.EnterRepeatPassword(password))
            },
            enabled = true,
            isError = state.ressetData.errorNewPassword,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 25.dp),
            label = stringResource(R.string.Повторите_пароль)
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    viewModel.sendEvent(ProfileEvent.SavePasword)
                }
                .padding(14.dp),
            textAlign = TextAlign.Center,
            text = stringResource(id = R.string.Сохранить),
            color = Gray,
            fontFamily = interFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            style = TextStyle(textDecoration = TextDecoration.Underline)
        )
    }
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 64.dp),
        colors = ButtonDefaults.buttonColors(BrightOrange),
        onClick = {
            viewModel.sendEvent(ProfileEvent.Exit)
        }) {
        Text(
            stringResource(id = R.string.выйти),
            fontFamily = interFamily,
            color = White,
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp
        )
    }
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
        colors = ButtonDefaults.buttonColors(Red),
        onClick = {
            viewModel.sendEvent(ProfileEvent.Delete)
        }) {
        Text(
            stringResource(id = R.string.Удалить_аккаунт),
            fontFamily = interFamily,
            color = White,
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp
        )
    }
}
