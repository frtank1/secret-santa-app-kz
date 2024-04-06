package kz.secret_santa_jusan.presentation.main

import android.os.Parcelable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import kz.secret_santa_jusan.core.views.SsText
import kz.secret_santa_jusan.presentation.registration.RegistrationEvent
import kz.secret_santa_jusan.ui.theme.BrightOrange
import kz.secret_santa_jusan.ui.theme.DarkGray
import kz.secret_santa_jusan.ui.theme.LightBlue
import kz.secret_santa_jusan.ui.theme.interFamily

@Parcelize
class MainScreen : CoreBaseScreen(), Parcelable {

    @Composable
    override fun Content() {
        val viewModel = getScreenModel<MainViewModel>()
        val navigator = LocalNavigator.currentOrThrow
        val navigationEvent = viewModel.navigationEvent.collectAsStateWithLifecycle().value.getValue()
        when(navigationEvent){
            is NavigationEvent.Default -> {}
            is NavigationEvent.Back -> navigator.pop()
            //is NavigationEvent.AuthRouter -> navigator.push(ScreenRegistry.get(AuthRouter.ProfileScreen()))
        }
        SubscribeError(viewModel)
        MainContent(viewModel = viewModel)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainContentPreview() {
    MainContent(MainViewModelPreview())
}


@Composable
fun MainContent(viewModel: IMainViewModel) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = LightBlue)
        //.verticalScroll(rememberScrollState())
    ){
        when (state) {
            is MainState.Default -> {
                    Image(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 64.dp),
                        painter = painterResource(id = R.drawable.santa03),
                        contentDescription = null,
                    )
                    SsText(
                        modifier = Modifier
                            .padding(top = 27.dp)
                            .fillMaxWidth(),
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
    }

}