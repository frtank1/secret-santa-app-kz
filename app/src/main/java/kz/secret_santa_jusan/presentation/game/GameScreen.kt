package kz.secret_santa_jusan.presentation.game

import android.os.Parcelable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
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
import kz.secret_santa_jusan.core.views.GameCard
import kz.secret_santa_jusan.core.views.SsText
import kz.secret_santa_jusan.core.views.TitleBar
import kz.secret_santa_jusan.data.game.models.GameModel
import kz.secret_santa_jusan.presentation.game.create.CreateScreen
import kz.secret_santa_jusan.presentation.invate.InvateScreen
import kz.secret_santa_jusan.ui.theme.BrightOrange
import kz.secret_santa_jusan.ui.theme.DarkGray
import kz.secret_santa_jusan.ui.theme.PaleBlue
import kz.secret_santa_jusan.ui.theme.interFamily

@Parcelize
class GameScreen : CoreBaseScreen(), Parcelable {

    @Composable
    override fun Content() {
        ShowBottomBar()
        val viewModel = getScreenModel<GameViewModel>()
        val navigator = LocalNavigator.currentOrThrow
        val navigationEvent =
            viewModel.navigationEvent.collectAsStateWithLifecycle().value
        when (navigationEvent) {
            is NavigationEvent.Default -> {}
            is NavigationEvent.Back -> navigator.pop()
            NavigationEvent.GoToCreate -> {
                navigator.push(
                    CreateScreen()
                )
            }

            is NavigationEvent.GoToCard -> {
                navigator.push(
                    InvateScreen(null,navigationEvent.gameModel)
                )
            }
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
        TitleBar(onClickBack = {
            viewModel.sendEvent(GameEvent.Back)
        })
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
                    notHaveGame( {viewModel.sendEvent(GameEvent.GoToCreate)})
                }

                is GameState.Init -> {
                    HaveGame(
                        viewModel,
                        state.list,
                        {viewModel.sendEvent(GameEvent.GoToCard(it))},
                        {viewModel.sendEvent(GameEvent.GoToCreate)}
                    )
                }
            }

        }
    }
}

@Composable
fun notHaveGame(
    onClick: (() -> Unit)? = null,
) {
    Column(
        Modifier.verticalScroll(rememberScrollState())
    ) {
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
                onClick?.invoke()
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

@Composable
fun HaveGame(
    viewModel: IGameViewModel,
    list: List<GameModel>,
    onClickCard: ((Int) -> Unit)? = null,
    onClickNavCard: (() -> Unit)? = null
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    val listState = rememberLazyListState()
    LazyColumn(
        state = listState,
    ) {
        itemsIndexed(
            list,
        ) { id, item ->
            GameCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 21.dp),
                onClick = {
                    onClickCard?.invoke(id)
                },
                title = item.name,
                count = item.participantCount.toString(),
                own = false
            )
        }
        item {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
                    .padding(horizontal = 25.dp),
                colors = ButtonDefaults.buttonColors(BrightOrange),
                onClick = {
                    onClickNavCard?.invoke()
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
}
