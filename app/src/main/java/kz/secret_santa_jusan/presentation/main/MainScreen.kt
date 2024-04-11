package kz.secret_santa_jusan.presentation.main

import android.os.Parcelable
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import kz.secret_santa_jusan.core.views.SsText
import kz.secret_santa_jusan.core.views.TitleBar
import kz.secret_santa_jusan.presentation.game.GameScreen
import kz.secret_santa_jusan.presentation.game.create.CreateEvent
import kz.secret_santa_jusan.presentation.game.create.CreateScreen
import kz.secret_santa_jusan.presentation.invate.adding.AddingEvent
import kz.secret_santa_jusan.presentation.registration.RegistrationScreen
import kz.secret_santa_jusan.ui.theme.BrightOrange
import kz.secret_santa_jusan.ui.theme.DarkGray
import kz.secret_santa_jusan.ui.theme.PaleBlue
import kz.secret_santa_jusan.ui.theme.White
import kz.secret_santa_jusan.ui.theme.interFamily

@Parcelize
class MainScreen(val isAuth:Boolean) : CoreBaseScreen(), Parcelable {

    @Composable
    override fun Content() {
        if(isAuth){
            ShowBottomBar()
        }
        val viewModel = getScreenModel<MainViewModel>()
        val navigator = LocalNavigator.currentOrThrow
        val navigationEvent =
            viewModel.navigationEvent.collectAsStateWithLifecycle().value.getValue()
        when (navigationEvent) {
            is NavigationEvent.Default -> {}
            is NavigationEvent.Back -> navigator.pop()
            NavigationEvent.GoToRegistration -> {
                navigator.push(
                    RegistrationScreen(
                    )
                )
            }

            NavigationEvent.GoToCreateGame -> {
                navigator.push(
                    CreateScreen()
                )
            }
        }
        viewModel.sendEvent(MainEvent.Init(isAuth))
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
    Column {
        TitleBar()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = PaleBlue)
                .padding(horizontal = 36.dp)
                .verticalScroll(rememberScrollState())
            ,
            horizontalAlignment = Alignment.CenterHorizontally,

        ) {
            when (state) {
                is MainState.IsAuth -> {
                        haveRegistration(viewModel)
                }

                is MainState.NotAuth -> {
                    notRegistration(viewModel)
                }
            }
        }
    }

}


@Composable
fun notRegistration(
    viewModel: IMainViewModel
) {
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
                viewModel.sendEvent(MainEvent.GoToRegistration)
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
fun haveRegistration(
    viewModel: IMainViewModel
) {
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
                .padding(top = 27.dp)
                .fillMaxWidth(),
            text = stringResource(id = R.string.Тайный_Санта),
            color = DarkGray,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            fontSize = 40.sp,
        )
        SsText(
            modifier = Modifier.clickable {
                viewModel.sendEvent(MainEvent.GoToCreateGame)
            },
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
                viewModel.sendEvent(MainEvent.GoToCreateGame)
            }) {
            Text(
                stringResource(id = R.string.Создай),
                fontFamily = interFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
        }
    }
}
