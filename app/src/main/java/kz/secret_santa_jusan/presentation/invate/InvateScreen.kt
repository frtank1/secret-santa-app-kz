package kz.secret_santa_jusan.presentation.invate

import android.os.Parcelable
import androidx.compose.foundation.Image
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
import kz.secret_santa_jusan.core.views.ProfileInfoCadr
import kz.secret_santa_jusan.core.views.SsText
import kz.secret_santa_jusan.core.views.TitleBar
import kz.secret_santa_jusan.data.game.models.GameModel
import kz.secret_santa_jusan.presentation.form.FormScreen
import kz.secret_santa_jusan.presentation.game.create.CreateScreen
import kz.secret_santa_jusan.presentation.invate.adding.AddingScreen
import kz.secret_santa_jusan.presentation.recepient.RecepientScreen
import kz.secret_santa_jusan.presentation.registration.RegistrationEvent
import kz.secret_santa_jusan.presentation.registration.RegistrationScreen
import kz.secret_santa_jusan.ui.theme.BrightOrange
import kz.secret_santa_jusan.ui.theme.DarkGray
import kz.secret_santa_jusan.ui.theme.PaleBlue
import kz.secret_santa_jusan.ui.theme.interFamily

@Parcelize
class InvateScreen(val link: String?, val gameModel: GameModel?=null) : CoreBaseScreen(), Parcelable {

    @Composable
    override fun Content() {
        ShowBottomBar()
        val viewModel = getScreenModel<InvateViewModel>()
        val navigator = LocalNavigator.currentOrThrow
        val navigationEvent =
            viewModel.navigationEvent.collectAsStateWithLifecycle().value.getValue()
        when (navigationEvent) {
            is NavigationEvent.Default -> {}
            is NavigationEvent.Back -> navigator.pop()
            is NavigationEvent.ContactWhitOrg -> {
            }
            is NavigationEvent.CreateCard -> {
                navigator.push(
                   FormScreen(navigationEvent.id)
                )
            }
            is NavigationEvent.GoToAddUser -> {
                navigator.push(
                    AddingScreen(navigationEvent.id)
                )
            }
            is NavigationEvent.ShowWard -> {
                navigator.push(
                    RecepientScreen(navigationEvent.gameModel)
                )

            }

            is NavigationEvent.GoToRegistration -> {
                navigator.push(
                    RegistrationScreen(navigationEvent.gameModel)
                )
            }
        }
        viewModel.sendEvent(InvateEvent.Init(link,gameModel))
        SubscribeError(viewModel)
        InvateContent(viewModel = viewModel)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun InvateContentPreview() {
    InvateContent(InvateViewModelPreview())
}

@Composable
fun InvateContent(viewModel: IInvateViewModel) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    Column {
        TitleBar(onClickBack = {
            viewModel.sendEvent(InvateEvent.Back)
        })
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = PaleBlue)
                .padding(horizontal = 36.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,

        ) {
            when (state) {
                is InvateState.ClosedScreen -> {
                    GameClosed(viewModel,state.name)

                }
                is InvateState.OrgScreen -> {
                    GameCreated(viewModel)
                }
                is InvateState.UserScreen -> {
                    InvatedUser(viewModel)
                }

                is InvateState.Default -> {
                }
            }
        }
    }
}


@Composable
fun GameCreated(viewModel: IInvateViewModel) {
    Column {
        SsText(
            modifier = Modifier
                .padding(top = 25.dp)
                .fillMaxWidth(),
            text = stringResource(id = R.string.Игра_создана),
            color = BrightOrange,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            fontSize = 40.sp,
        )
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
            text = stringResource(id = R.string.Пока_ничего_нет),
            color = DarkGray,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            fontSize = 15.sp,
        )
        SsText(
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(id = R.string.Добавьте_участников_чтобы_игра_началась),
            color = DarkGray,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center,
            fontSize = 10.sp
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 66.dp)
                .padding(horizontal = 25.dp),
            colors = ButtonDefaults.buttonColors(BrightOrange),
            onClick = {
                viewModel.sendEvent(InvateEvent.ReShuffle)
            }) {
            Text(
                stringResource(id = R.string.Жеребевка),
                fontFamily = interFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .padding(horizontal = 25.dp),
            colors = ButtonDefaults.buttonColors(BrightOrange),
            onClick = {
                viewModel.sendEvent(InvateEvent.GoToAddUser())
            }) {
            Text(
                stringResource(id = R.string.Добавить_участников),
                fontFamily = interFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
        }
    }
}

@Composable
fun InvatedUser(viewModel: IInvateViewModel) {
    Column {
        SsText(
            modifier = Modifier
                .padding(top = 25.dp)
                .fillMaxWidth(),
            text = stringResource(id = R.string.Организатор_приглашает_Вас_в_игру),
            color = DarkGray,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
        )
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
            text = stringResource(id = R.string.Заполните_карточку_чтоб_принять_участие_в_игре),
            color = DarkGray,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center,
            fontSize = 10.sp
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 76.dp)
                .padding(horizontal = 15.dp),
            colors = ButtonDefaults.buttonColors(BrightOrange),
            onClick = {
                viewModel.sendEvent(InvateEvent.CreateCard())
            }) {
            Text(
                stringResource(id = R.string.Создать_карточку_участника),
                fontFamily = interFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp
            )
        }
    }
}


@Composable
fun GameClosed(viewModel: IInvateViewModel,name:String) {
    Column {
        ProfileInfoCadr(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 47.dp),
            name = name,
            email = stringResource(id = R.string.Связаться_с_Организатором)
        )
        SsText(
            modifier = Modifier
                .padding(top = 90.dp)
                .fillMaxWidth(),
            text = stringResource(id = R.string.вопросительный),
            color = BrightOrange,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            fontSize = 185.sp,
        )

        SsText(
            modifier = Modifier
                .padding(top = 80.dp)
                .fillMaxWidth(),
            text = stringResource(id = R.string.Жеребевка_завершена),
            color = DarkGray,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center,
            fontSize = 10.sp
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp)
                .padding(horizontal = 25.dp),
            colors = ButtonDefaults.buttonColors(BrightOrange),
            onClick = {
                viewModel.sendEvent(InvateEvent.ShowWard())
            }) {
            Text(
                stringResource(id = R.string.Узнать_Подопечного),
                fontFamily = interFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
        }
    }
}
