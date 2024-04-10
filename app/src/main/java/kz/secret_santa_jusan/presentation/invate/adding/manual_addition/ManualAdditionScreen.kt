package kz.secret_santa_jusan.presentation.invate.adding.manual_addition

import android.os.Parcelable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
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
import kz.secret_santa_jusan.core.Utils
import kz.secret_santa_jusan.core.base.CoreBaseScreen
import kz.secret_santa_jusan.core.views.EditText
import kz.secret_santa_jusan.core.views.SsText
import kz.secret_santa_jusan.core.views.TitleBar
import kz.secret_santa_jusan.ui.theme.BrightOrange
import kz.secret_santa_jusan.ui.theme.DarkGray
import kz.secret_santa_jusan.ui.theme.PaleBlue
import kz.secret_santa_jusan.ui.theme.White
import kz.secret_santa_jusan.ui.theme.interFamily

@Parcelize
class ManualAdditionScreen(val id: String) : CoreBaseScreen(), Parcelable {

    @Composable
    override fun Content() {
        val viewModel = getScreenModel<ManualAdditionViewModel>()
        val navigator = LocalNavigator.currentOrThrow
        val navigationEvent =
            viewModel.navigationEvent.collectAsStateWithLifecycle().value.getValue()
        when (navigationEvent) {
            is NavigationEvent.Default -> {}
            is NavigationEvent.Back -> navigator.pop()
        }
        SubscribeError(viewModel)
        ManualAdditionContent(viewModel = viewModel)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ManualAdditionContentPreview() {
    ManualAdditionContent(ManualAdditionViewModelPreview())
}

@Composable
fun ManualAdditionContent(viewModel: IManualAdditionViewModel) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    val context = LocalContext.current
    Column {
        TitleBar(onClickBack = {
            viewModel.sendEvent(ManualAdditionEvent.Back)
        })
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = PaleBlue)
                .padding(horizontal = 36.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (state) {
                is ManualAdditionState.Default -> {
                    SsText(
                        modifier = Modifier
                            .padding(top = 86.dp)
                            .fillMaxWidth(),
                        text = stringResource(id = R.string.Добавить_участников),
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        fontSize = 23.sp,
                    )
                    val listState = rememberLazyListState()
                    Box(modifier = Modifier.weight(1f)) {
                        LazyColumn(
                            state = listState,
                        ) {
                            itemsIndexed(
                                state.list,
                            ) { id, item ->
                                Item(
                                    id = id,
                                    name = state.list[id].name ?: "",
                                    email = state.list[id].email ?: "",
                                    viewModel
                                )
                            }
                            item {
                                Row(
                                    modifier = Modifier
                                        .padding(top = 15.dp)
                                        .clickable {
                                            viewModel.sendEvent(ManualAdditionEvent.addNewUSer)
                                        },
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.plus),
                                        contentDescription = null,
                                        colorFilter = ColorFilter.tint(White),
                                        modifier = Modifier
                                            .size(width = 25.dp, height = 25.dp)
                                            .clip(shape = RoundedCornerShape(100.dp))
                                            .background(BrightOrange)
                                            .padding(7.dp)
                                    )
                                    SsText(
                                        modifier = Modifier
                                            .padding(start = 5.dp),
                                        text = stringResource(id = R.string.Добавить_участников),
                                        fontWeight = FontWeight.Normal,
                                        textAlign = TextAlign.Center,
                                        fontSize = 12.sp,
                                    )
                                }
                            }
                        }
                    }
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 28.dp, bottom = 35.dp)
                            .padding(horizontal = 30.dp),
                        colors = ButtonDefaults.buttonColors(BrightOrange),
                        onClick = {
                            viewModel.sendEvent(ManualAdditionEvent.SendLink)
                            Utils.sendToat("Отправлено",context)
                        }) {
                        Text(
                            stringResource(id = R.string.Пригласить),
                            fontFamily = interFamily,
                            color = White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    }
                }

                is ManualAdditionState.Done -> {
                    InvatedUser()
                }
            }
        }
    }
}

@Composable
fun Item(
    id: Int,
    name: String,
    email: String,
    viewModel: IManualAdditionViewModel
) {
    Column(
        modifier = Modifier.padding(vertical = 14.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SsText(
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(id = R.string.Участник) + "${id}",
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center,
            fontSize = 15.sp,
        )
        EditText(
            value = name,
            onValueChange = { name ->
                viewModel.sendEvent(ManualAdditionEvent.EnterUserName(id, name))
            },
            enabled = true,
            isError = false,
            modifier = Modifier
                .fillMaxWidth(),
            label = stringResource(R.string.Ваше_Имя)
        )
        EditText(
            value = email,
            onValueChange = { email ->
                viewModel.sendEvent(ManualAdditionEvent.EnterUserEmail(id, email))
            },
            enabled = true,
            isError = false,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 14.dp),
            label = stringResource(R.string.Ваше_Имя)
        )
    }
}


@Composable
fun InvatedUser() {
    Column {
        SsText(
            modifier = Modifier
                .padding(top = 25.dp)
                .fillMaxWidth(),
            text = stringResource(id = R.string.Приглашение_отправлены),
            color = BrightOrange,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
        )
        Image(
            modifier = Modifier
                .padding(top = 32.dp)
                .align(Alignment.CenterHorizontally),
            painter = painterResource(id = R.drawable.santa02),
            contentDescription = null,
        )

        SsText(
            modifier = Modifier
                .padding(top = 9.dp)
                .fillMaxWidth(),
            text = stringResource(id = R.string.Уведомление_об_уведомление),
            color = DarkGray,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center,
            fontSize = 10.sp
        )
    }
}