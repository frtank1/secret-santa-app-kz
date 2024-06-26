package kz.secret_santa_jusan.presentation.invate.adding

import android.os.Parcelable
import androidx.compose.foundation.BorderStroke
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
import kz.secret_santa_jusan.presentation.form.FormScreen
import kz.secret_santa_jusan.presentation.invate.adding.link.LinkScreen
import kz.secret_santa_jusan.presentation.invate.adding.manual_addition.ManualAdditionScreen
import kz.secret_santa_jusan.ui.theme.BrightOrange
import kz.secret_santa_jusan.ui.theme.PaleBlue
import kz.secret_santa_jusan.ui.theme.White
import kz.secret_santa_jusan.ui.theme.interFamily

@Parcelize
class AddingScreen(val id:String) : CoreBaseScreen(), Parcelable {

    @Composable
    override fun Content() {
        ShowBottomBar()
        val viewModel = getScreenModel<AddingViewModel>()
        val navigator = LocalNavigator.currentOrThrow
        val navigationEvent =
            viewModel.navigationEvent.collectAsStateWithLifecycle().value.getValue()
        when (navigationEvent) {
            is NavigationEvent.Default -> {}
            is NavigationEvent.Back -> navigator.pop()
            is NavigationEvent.GoToAdding -> {
                navigator.push(ManualAdditionScreen(navigationEvent.id))
            }
            is NavigationEvent.GoToLink -> {
                navigator.push(LinkScreen(navigationEvent.id))
            }
            is NavigationEvent.GoToOwnAdd -> {
                navigator.push(
                    FormScreen(navigationEvent.id)
                )
            }
        }
        viewModel.sendEvent(AddingEvent.Default(id))
        SubscribeError(viewModel)
        AddingContent(viewModel = viewModel)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddingContentPreview() {
    AddingContent(AddingViewModelPreview())
}

@Composable
fun AddingContent(viewModel: IAddingViewModel) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    Column {
        TitleBar(onClickBack = {
            viewModel.sendEvent(AddingEvent.Back)
        })
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = PaleBlue)
                .padding(horizontal = 36.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (state) {
                is AddingState.Default -> {
                    AddingMenu(state.id,viewModel)
                }
            }
        }
    }

}


@Composable
fun AddingMenu(id: String,viewModel: IAddingViewModel) {
    Column(
        Modifier.padding(horizontal = 12.dp)
    ) {
        SsText(
            modifier = Modifier
                .padding(top = 86.dp)
                .fillMaxWidth(),
            text = stringResource(id = R.string.Добавить_участников),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            fontSize = 27.sp,
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 96.dp),
            colors = ButtonDefaults.buttonColors(White),
            border = BorderStroke(2.dp, BrightOrange),
            onClick = {
                viewModel.sendEvent(AddingEvent.GoToOwnAdd(id))
            }) {
            Text(
                stringResource(id = R.string.Создать_свою_карточку_участника),
                fontFamily = interFamily,
                textAlign = TextAlign.Center,
                color = BrightOrange,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 19.dp),
            colors = ButtonDefaults.buttonColors(White),
            border = BorderStroke(2.dp, BrightOrange),
            onClick = {
                viewModel.sendEvent(AddingEvent.GoToAdding(id))
            }) {
            Text(
                stringResource(id = R.string.Добавить_участников_вручную),
                fontFamily = interFamily,
                color = BrightOrange,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 19.dp),
            colors = ButtonDefaults.buttonColors(White),
            border = BorderStroke(2.dp, BrightOrange),
            onClick = {
                viewModel.sendEvent(AddingEvent.GoToLink(id))
            }) {
            Text(
                stringResource(id = R.string.Пригласить_по_ссылке),
                fontFamily = interFamily,
                color = BrightOrange,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
        }
    }
}

