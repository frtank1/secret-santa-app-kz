package kz.secret_santa_jusan.presentation.game

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
import kz.secret_santa_jusan.core.views.TitleBar
import kz.secret_santa_jusan.ui.theme.BrightOrange
import kz.secret_santa_jusan.ui.theme.DarkGray
import kz.secret_santa_jusan.ui.theme.LightBlue
import kz.secret_santa_jusan.ui.theme.PaleBlue
import kz.secret_santa_jusan.ui.theme.interFamily

@Parcelize
class GameScreen : CoreBaseScreen(), Parcelable {

    @Composable
    override fun Content() {
        val viewModel = getScreenModel<GameViewModel>()
        val navigator = LocalNavigator.currentOrThrow
        val navigationEvent = viewModel.navigationEvent.collectAsStateWithLifecycle().value.getValue()
        when(navigationEvent){
            is NavigationEvent.Default -> {}
            is NavigationEvent.Back -> navigator.pop()
            //is NavigationEvent.AuthRouter -> navigator.push(ScreenRegistry.get(AuthRouter.ProfileScreen()))
        }
        SubscribeError(viewModel)
        GameContent(viewModel = viewModel)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GameContentPreview() {
    GameContent(GameViewModelPreview())
}


@Composable
fun GameContent(viewModel: IGameViewModel) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    Column {
        TitleBar()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = PaleBlue)
                .padding(horizontal = 36.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SsText(
                modifier = Modifier
                    .padding(top = 25.dp)
                    .fillMaxWidth(),
                text = stringResource(id = R.string.Мои_игры),
                color = BrightOrange,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                fontSize = 40.sp,
            )
            when (state) {
                is GameState.Default -> {
                    notHaveGame()
                }
            }
        }
    }
}

@Composable
fun notHaveGame() {
    Column {

        Image(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            painter = painterResource(id = R.drawable.santa01),
            contentDescription = null,
        )
        SsText(
            modifier = Modifier
                .padding(top = 9.dp)
                .fillMaxWidth(),
            text = stringResource(id = R.string.Пока_что_Вы_не_участвуете_в_играх),
            color = DarkGray,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            fontSize = 15.sp,
        )
        SsText(
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(id = R.string.Создайте_или_вступите_в_игру),
            color = DarkGray,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center,
            fontSize = 10.sp
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 76.dp)
                .padding(horizontal = 25.dp),
            colors = ButtonDefaults.buttonColors(BrightOrange),
            onClick = {
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
