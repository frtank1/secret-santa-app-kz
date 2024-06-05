package kz.secret_santa_jusan.presentation.game.create

import android.os.Parcelable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
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
import kz.secret_santa_jusan.core.views.TitleBar
import kz.secret_santa_jusan.presentation.game.GameEvent
import kz.secret_santa_jusan.presentation.invate.InvateScreen
import kz.secret_santa_jusan.ui.theme.BrightOrange
import kz.secret_santa_jusan.ui.theme.DarkGray
import kz.secret_santa_jusan.ui.theme.Gray
import kz.secret_santa_jusan.ui.theme.LightBlue
import kz.secret_santa_jusan.ui.theme.PaleBlue
import kz.secret_santa_jusan.ui.theme.White
import kz.secret_santa_jusan.ui.theme.interFamily

@Parcelize
class CreateScreen : CoreBaseScreen(), Parcelable {

    @Composable
    override fun Content() {
        ShowBottomBar()
        val viewModel = getScreenModel<CreateViewModel>()
        val navigator = LocalNavigator.currentOrThrow
        val navigationEvent =
            viewModel.navigationEvent.collectAsStateWithLifecycle().value.getValue()
        when (navigationEvent) {
            is NavigationEvent.Default -> {}
            is NavigationEvent.Back -> navigator.pop()
            //is NavigationEvent.AuthRouter -> navigator.push(ScreenRegistry.get(AuthRouter.ProfileScreen()))
            is NavigationEvent.GoToInvate -> {
                navigator.replace(InvateScreen(null,navigationEvent.gameModel))
            }
        }
        SubscribeError(viewModel)
        CreateContent(viewModel = viewModel)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CreateContentPreview() {
    CreateContent(CreateViewModelPreview())
}


@Composable
fun CreateContent(viewModel: ICreateViewModel) {
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
            when (state) {
                is CreateState.Default -> {
                    CreateMenu(viewModel)
                }
            }
        }
    }
}


@Composable
fun CreateMenu(viewModel: ICreateViewModel) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    Column {
        SsText(
            modifier = Modifier
                .padding(top = 45.dp)
                .fillMaxWidth(),
            text = stringResource(id = R.string.Создать_игру),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            fontSize = 27.sp,
        )
        EditText(
            value = state.createData.name ?: "-",
            onValueChange = { name ->
                viewModel.sendEvent(CreateEvent.EnterName(name))
            },
            enabled = true,
            isError = false,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 28.dp),
            label = stringResource(R.string.Название_Игры),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp),
            textAlign = TextAlign.Center,
            text = stringResource(id = R.string.Придумайте_уникальный_идентификатор_для_коробки),
            color = Gray,
            fontFamily = interFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 5.sp,
        )
        MaxSumCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            onClick = {
                viewModel.sendEvent(CreateEvent.ShowSum(it))
            },
            state.createData.showSum?:false
        )
        if (state.createData.showSum){
            EditText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
                    .padding(top = 12.dp),
                value = if(state.createData.maxPrice!! >=1)state.createData.maxPrice.toString()else "",
                textAlign = TextAlign.End,
                fontSize = 10.sp,
                onValueChange = { sum ->
                    viewModel.sendEvent(CreateEvent.EnterSum(sum))
                },
                enabled = true,
                isError = state.createData.erroSum,
                label = stringResource(R.string.Укажите_максимальную_стоимость_подарка),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 74.dp),
            colors = ButtonDefaults.buttonColors(BrightOrange),
            onClick = {
                viewModel.sendEvent(CreateEvent.CreateGame)
            }) {
            Text(
                stringResource(id = R.string.Создать_игру),
                fontFamily = interFamily,
                color = White,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
        }
    }
}

@Composable
fun MaxSumCard(
    modifier: Modifier = Modifier
        .fillMaxWidth(),
    onClick: ((Boolean) -> Unit)? = null,
    boolean: Boolean
) {

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = White
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 14.dp),
                text = stringResource(id = R.string.Максимальная_стоимость_подарка),
                color = DarkGray,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                fontSize = 12.sp,
            )
            Switch(
                modifier = Modifier.padding(end = 10.dp),
                checked = boolean,
                onCheckedChange = { isChecked ->
                    onClick?.invoke(isChecked)
                },
                colors = SwitchColors(
                    checkedThumbColor = PaleBlue,
                    checkedTrackColor = LightBlue,
                    checkedBorderColor = LightBlue,
                    checkedIconColor = LightBlue,
                    uncheckedThumbColor = PaleBlue,
                    uncheckedTrackColor = LightBlue,
                    uncheckedBorderColor = LightBlue,
                    uncheckedIconColor = LightBlue,
                    disabledCheckedThumbColor = DarkGray,
                    disabledCheckedTrackColor = Gray,
                    disabledCheckedBorderColor = DarkGray,
                    disabledCheckedIconColor = Gray,
                    disabledUncheckedThumbColor = DarkGray,
                    disabledUncheckedTrackColor = Gray,
                    disabledUncheckedBorderColor = DarkGray,
                    disabledUncheckedIconColor = Gray
                )
            )
        }
    }
}