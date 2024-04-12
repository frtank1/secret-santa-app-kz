package kz.secret_santa_jusan.presentation.recepient

import android.os.Parcelable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import kz.secret_santa_jusan.core.views.ProfileInfoCadr
import kz.secret_santa_jusan.core.views.SsText
import kz.secret_santa_jusan.core.views.TitleBar
import kz.secret_santa_jusan.data.game.models.GameModel
import kz.secret_santa_jusan.data.recepient.models.PercepientModel
import kz.secret_santa_jusan.ui.theme.BrightOrange
import kz.secret_santa_jusan.ui.theme.DarkGray
import kz.secret_santa_jusan.ui.theme.Gray
import kz.secret_santa_jusan.ui.theme.PaleBlue
import kz.secret_santa_jusan.ui.theme.White
import kz.secret_santa_jusan.ui.theme.interFamily

@Parcelize
class RecepientScreen(val gameModel: GameModel?) : CoreBaseScreen(), Parcelable {

    @Composable
    override fun Content() {
        val viewModel = getScreenModel<RecepientViewModel>()
        val navigator = LocalNavigator.currentOrThrow
        val navigationEvent =
            viewModel.navigationEvent.collectAsStateWithLifecycle().value.getValue()
        when (navigationEvent) {
            is NavigationEvent.Default -> {}
            is NavigationEvent.Back -> navigator.pop()
            else -> {}
        }
        viewModel.sendEvent(RecepientEvent.Init(gameModel))
        SubscribeError(viewModel)
        RecepientContent(viewModel = viewModel)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RecepientContentPreview() {
    RecepientContent(RecepientViewModelPreview())
}

@Composable
fun RecepientContent(viewModel: IRecepientViewModel) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    Column {
        TitleBar(onClickBack = {
            viewModel.sendEvent(RecepientEvent.Back)
        })
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = PaleBlue)
                .padding(horizontal = 36.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (state) {
                is RecepientState.Default -> {
                    СardRecepient(viewModel, state?.name ?: "", state?.userName ?: "")
                }

                is RecepientState.Show -> {
                    Show(viewModel,state?.name?:"",state.percepient)
                }
            }
        }
    }
}


@Composable
fun СardRecepient(viewModel: IRecepientViewModel, name: String, nameUser: String) {
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
                .padding(top = 150.dp)
                .fillMaxWidth(),
            text = stringResource(id = R.string.готово),
            color = BrightOrange,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            fontSize = 185.sp,
        )

        SsText(
            modifier = Modifier
                .padding(top = 100.dp)
                .fillMaxWidth(),
            text = stringResource(id = R.string.Ваш_подопечный,nameUser),
            color = DarkGray,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center,
            fontSize = 10.sp
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
                .padding(horizontal = 25.dp),
            colors = ButtonDefaults.buttonColors(BrightOrange),
            onClick = {
            viewModel.sendEvent(RecepientEvent.ShowGift)
            }) {
            Text(
                stringResource(id = R.string.открыть_карточку),
                fontFamily = interFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
        }
    }
}

@Composable
fun Show(viewModel: IRecepientViewModel, name: String, percepient: PercepientModel) {
    Column {
        ProfileInfoCadr(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 47.dp),
            name = name,
            email = stringResource(id = R.string.Связаться_с_Организатором)
        )
        percepient.gifteeEmail?.let { stringResource(id = R.string.Карточка_Имя, it) }?.let {
            SsText(
                modifier = Modifier
                    .padding(top = 150.dp)
                    .fillMaxWidth(),
                text = it,
                color = Gray,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                fontSize = 15.sp,
            )
        }
        for (gift in percepient.wishlistDescriptions!!) {
            SsText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp),
                text = stringResource(id = R.string.gift_no),
                color = DarkGray,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                fontSize = 15.sp
            )
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(32.dp),
                colors = CardDefaults.cardColors(
                    containerColor = White
                )
            ) {
                SsText(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = gift ?: "",
                    color = Gray,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    fontSize = 15.sp
                )
            }
        }
    }
}