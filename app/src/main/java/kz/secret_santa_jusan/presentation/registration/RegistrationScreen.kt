package kz.secret_santa_jusan.presentation.registration

import android.os.Parcelable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
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
import kz.secret_santa_jusan.core.views.AutoCompleteEditText
import kz.secret_santa_jusan.core.views.SsText
import kz.secret_santa_jusan.core.views.TitleBar
import kz.secret_santa_jusan.ui.theme.BrightOrange
import kz.secret_santa_jusan.ui.theme.DarkGray
import kz.secret_santa_jusan.ui.theme.Gray
import kz.secret_santa_jusan.ui.theme.LightGrey
import kz.secret_santa_jusan.ui.theme.PaleBlue
import kz.secret_santa_jusan.ui.theme.interFamily

@Parcelize
class RegistrationScreen : CoreBaseScreen(), Parcelable {

    @Composable
    override fun Content() {
        val viewModel = getScreenModel<RegistrationViewModel>()
        val navigator = LocalNavigator.currentOrThrow
        val navigationEvent =
            viewModel.navigationEvent.collectAsStateWithLifecycle().value.getValue()
        when (navigationEvent) {
            is NavigationEvent.Default -> {}
            is NavigationEvent.Back -> navigator.pop()
            //is NavigationEvent.AuthRouter -> navigator.push(ScreenRegistry.get(AuthRouter.ProfileScreen()))
        }
        SubscribeError(viewModel)
        RegistrationContent(viewModel = viewModel)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegistrationContentPreview() {
    RegistrationContent(RegistrationViewModelPreview())
}


@Composable
fun RegistrationContent(viewModel: IRegistrationViewModel) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    Column {
        TitleBar(onClickBack = {})
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = PaleBlue)
                .padding(horizontal = 36.dp),
            horizontalAlignment = Alignment.CenterHorizontally
            //.verticalScroll(rememberScrollState())
        ) {
            when (state) {
                is RegistrationState.Default -> {
                    registrationMenu()
                }
            }
        }
    }
}

@Composable
fun mainRegistration() {
    Column {
        Image(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 64.dp),
            painter = painterResource(id = R.drawable.santa03),
            contentDescription = null,
        )
        SsText(
            modifier = Modifier
                .padding(top = 27.dp),
            text = stringResource(id = R.string.Тайный_Санта),
            color = DarkGray,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            fontSize = 40.sp,
        )
        SsText(
            text = stringResource(id = R.string.Организуй_тайный_обмен_подарками_между_друзьями_или_коллегами),
            color = DarkGray,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center,
            fontSize = 10.sp
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 27.dp)
                .padding(horizontal = 25.dp),
            colors = ButtonDefaults.buttonColors(BrightOrange),
            onClick = {
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

@Composable
fun registrationMenu() {
    Column {
        SsText(
            text = stringResource(id = R.string.Регистрация),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            fontSize = 40.sp,
        )
        AutoCompleteEditText(
            modifier = Modifier
                .padding(top = 54.dp)
                .fillMaxWidth(),
            onSelected = {
            },
            onValueChange = {
            },
            label = stringResource(R.string.Ваше_Имя),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        )
        AutoCompleteEditText(
            modifier = Modifier
                .padding(top = 12.dp)
                .fillMaxWidth(),
            onSelected = {
            },
            onValueChange = {
            },
            label = stringResource(R.string.Ваш_mail),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        )
        spliterOr(
            modifier = Modifier
                .padding(top = 24.dp)
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            colors = ButtonDefaults.buttonColors(LightGrey),
            onClick = {
            }) {
            Text(
                stringResource(id = R.string.sign_googe),
                fontFamily = interFamily,
                color = DarkGray,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
        }
        AgreeText()
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 126.dp)
                .padding(horizontal = 25.dp),
            colors = ButtonDefaults.buttonColors(BrightOrange),
            onClick = {
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

@Composable
fun spliterOr(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Divider(color = Gray, modifier = Modifier.weight(1f))
        Text(
            modifier = Modifier.padding(horizontal = 8.dp),
            text = stringResource(id = R.string.или),
            color = Gray,
            fontFamily = interFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 10.sp
        )
        Divider(color = Gray, modifier = Modifier.weight(1f))
    }

}

@Composable
fun AgreeText(){
    Row(modifier = Modifier.padding(top = 10.dp)) {
        Text(
            text = stringResource(id = R.string.Регистрируясь_вы_даете_согласие_на),
            color = Gray,
            fontFamily = interFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 10.sp
        )
        Text(
            modifier = Modifier.padding(horizontal = 2.dp),
            text = stringResource(id = R.string.обработку_персональных_данных),
            color = Gray,
            fontFamily = interFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 10.sp,
            style = TextStyle(textDecoration = TextDecoration.Underline)
        )
    }
}