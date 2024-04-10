package kz.secret_santa_jusan.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.lifecycle.ScreenLifecycleStore
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import kz.secret_santa_jusan.R

import kz.secret_santa_jusan.core.base.CoreBaseActivity
import kz.secret_santa_jusan.core.navigation.ScreenLifecycleOwner
import kz.secret_santa_jusan.core.storage.GlobalStorage
import kz.secret_santa_jusan.presentation.game.GameScreen
import kz.secret_santa_jusan.presentation.main.MainScreen
import kz.secret_santa_jusan.presentation.profile.ProfileScreen
import kz.secret_santa_jusan.ui.theme.BrightOrange
import kz.secret_santa_jusan.ui.theme.DarkGray
import org.koin.androidx.compose.KoinAndroidContext
import kz.secret_santa_jusan.ui.theme.SicretsantajusanTheme
import kz.secret_santa_jusan.ui.theme.White

class MainActivity : CoreBaseActivity() {
    private val bottomBarViewModel: BottomBarViewModel by viewModels()
    private val mainViewModel: MainViewModel by viewModels()
    override fun hideBottomBar() {
        bottomBarViewModel.sendEvent(BottomBarEvent.HideBottomBar)
    }

    override fun showBottomBar() {
        bottomBarViewModel.sendEvent(BottomBarEvent.ShowBottomBar)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val startScreen = if (GlobalStorage.getAuthToken() != null) {
                MainScreen(true)
            } else {
                MainScreen(false)
            }
            val bottomBarState = bottomBarViewModel.state.collectAsStateWithLifecycle().value
            val nav = bottomBarViewModel.navigationEvent.collectAsStateWithLifecycle().value.getValue()
            KoinAndroidContext() {
                        Navigator(
                            screen = startScreen,
                            content = { navigator ->
                                remember(navigator.lastItem) {
                                    ScreenLifecycleStore.get(navigator.lastItem) {
                                        ScreenLifecycleOwner()
                                    }
                                }
                                SicretsantajusanTheme {
                                    Box(modifier = Modifier.fillMaxSize()) {
                                        Column(modifier = Modifier.fillMaxSize()) {
                                            Box(modifier = Modifier.weight(1f)) {
                                                CurrentScreen()
                                            }
                                            if (bottomBarState.bottomBar.showingBottomBar) {
                                                BottomBar(bottomBarViewModel)
                                            }
                                        }
                                        val mainState = mainViewModel.state.collectAsStateWithLifecycle().value
                                    }
                                }
                                when(nav){
                                    is NavigationEvent.Default -> {}
                                    is NavigationEvent.Game -> {
                                        navigator.replaceAll(GameScreen())
                                    }
                                    is NavigationEvent.MainPage -> {
                                        navigator.replaceAll(MainScreen(true))
                                    }
                                    is NavigationEvent.Profile -> {
                                        navigator.replaceAll(ProfileScreen())
                                    }
                                }
                            }
                        )
            }
        }

    }
}


@Composable
fun BottomBar(viewModel: IBottomBarViewModel){
    val state = viewModel.state.collectAsStateWithLifecycle().value
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = White),
        horizontalArrangement = Arrangement.Center
    ){
        Item (
            modifier = Modifier.weight(1f),
            state.bottomBar.selectMainPage,
            R.drawable.home_outline,
            {
                viewModel.sendEvent(BottomBarEvent.ClickMainPage(true))
            }
        )
        Item (
            modifier = Modifier.weight(1f),
            state.bottomBar.selectGame,
            R.drawable.gift_card_svgrepo_com,
            {
                viewModel.sendEvent(BottomBarEvent.ClickGame)
            }
        )
        Item (
            modifier = Modifier.weight(1f),
            state.bottomBar.selectProfile,
            R.drawable.person_outline,
            {
                viewModel.sendEvent(BottomBarEvent.ClickProfile)
            }
        )
    }
}


@Composable
fun Item(modifier: Modifier, isSelected:Boolean, iconRes: Int, onClick: ()-> Unit){
    Column(
        modifier = modifier
            .clip(shape = RoundedCornerShape(16.dp))
            .clickable {
                onClick.invoke()
            }
            .padding(top = 12.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        val color = if(isSelected) BrightOrange else DarkGray
        val backgroundIcon = if(isSelected)
            Modifier
                .padding(horizontal = 4.dp)
        else Modifier
        Box() {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = "",
                tint = color,
                modifier = backgroundIcon
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )
        }
    }
}