package kz.secret_santa_jusan.presentation.form.my_wishlist

import android.os.Parcelable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
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
import kz.secret_santa_jusan.core.views.EditText
import kz.secret_santa_jusan.core.views.SsText
import kz.secret_santa_jusan.core.views.TitleBar
import kz.secret_santa_jusan.ui.theme.BrightOrange
import kz.secret_santa_jusan.ui.theme.DarkGray
import kz.secret_santa_jusan.ui.theme.Gray
import kz.secret_santa_jusan.ui.theme.PaleBlue
import kz.secret_santa_jusan.ui.theme.White

@Parcelize
class MyWishlistScreen(val id: String) : CoreBaseScreen(), Parcelable {

    @Composable
    override fun Content() {
        ShowBottomBar()
        val viewModel = getScreenModel<MyWishlistViewModel>()
        val navigator = LocalNavigator.currentOrThrow

        // You might want to track navigation events similarly to the MainScreen
        // ...

        val navigationEvent =
            viewModel.navigationEvent.collectAsStateWithLifecycle().value.getValue()
        when (navigationEvent) {
            is NavigationEvent.Default -> {}
            is NavigationEvent.Back -> navigator.pop()
            is NavigationEvent.Default -> {
            }

        }
        CreateGameContent(viewModel = viewModel)
        viewModel.sendEvent(MyWishlistEvent.Init(id))
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MyWishlistScreenContentPreview() {
    CreateGameContent(MyWishlistViewModelPreview())
}

@Composable
fun CreateGameContent(viewModel: IMyWishlistViewModel) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    Column {
        TitleBar(onClickBack = {
            viewModel.sendEvent(MyWishlistEvent.Back)
        })
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = PaleBlue)
                .padding(horizontal = 36.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when(state){
                is MyWishlistState.Default -> {
                    MyWishlist(viewModel,state.gifts)
                }
                is MyWishlistState.Done -> {
                    CardCreated()
                }
            }

        }

    }
}

@Composable
fun MyWishlist(
    viewModel: IMyWishlistViewModel,
    list: List<String>
) {
    Column {
        Spacer(modifier = Modifier.height(50.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = (R.string.my_wishlist)),
            color = DarkGray,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            fontSize = 25.sp,

            )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = (R.string.my_wishlist_desc)),
            color = DarkGray,
            textAlign = TextAlign.Center,
            fontSize = 13.sp,
        )

        Spacer(modifier = Modifier.height(50.dp))
        Box(modifier = Modifier.weight(1f)) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                itemsIndexed(list) { index, gift ->
                    EditText(
                        value = gift,
                        onValueChange = { value ->
                            viewModel.sendEvent(MyWishlistEvent.EnterGift(value, index))
                        },
                        enabled = true,
                        isError = false,
                        modifier = Modifier
                            .fillMaxWidth(),
                        label = stringResource(R.string.gift_no, (index + 1))
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(50.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    viewModel.sendEvent(MyWishlistEvent.AddAnotherGift)
                }) {
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
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = stringResource(id = R.string.add_another_gift))
        }
        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                viewModel.sendEvent(MyWishlistEvent.CreateMyWishlist)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 25.dp)
                .align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(BrightOrange)
        ) {
            Text(
                text = stringResource(id = R.string.next),
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(id = (R.string.my_wishlist_info)),
            color = Gray,
            textAlign = TextAlign.Center,
            fontSize = 8.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}


@Composable
fun CardCreated() {
    Column {
        SsText(
            modifier = Modifier
                .padding(top = 25.dp)
                .fillMaxWidth(),
            text = stringResource(id = R.string.Карточка_участника_создана),
            color = BrightOrange,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
        )
        Image(
            modifier = Modifier
                .padding(top = 32.dp)
                .align(Alignment.CenterHorizontally),
            painter = painterResource(id = R.drawable.santa03),
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