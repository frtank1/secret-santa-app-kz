package kz.secret_santa_jusan.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.core.lifecycle.ScreenLifecycleStore
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator

import kz.secret_santa_jusan.core.base.CoreBaseActivity
import kz.secret_santa_jusan.core.navigation.ScreenLifecycleOwner
import kz.secret_santa_jusan.presentation.example.ExampleScreen
import kz.secret_santa_jusan.presentation.main.MainScreen
import kz.secret_santa_jusan.presentation.registration.RegistrationRouter
import kz.secret_santa_jusan.presentation.registration.RegistrationScreen
import org.koin.androidx.compose.KoinAndroidContext
import kz.secret_santa_jusan.ui.theme.SicretsantajusanTheme

class MainActivity : CoreBaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KoinAndroidContext() {
                SicretsantajusanTheme {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        Navigator(
                            screen = MainScreen(false),
                            content = { navigator ->
                                remember(navigator.lastItem) {
                                    ScreenLifecycleStore.get(navigator.lastItem) {
                                        ScreenLifecycleOwner()
                                    }
                                }
                                CurrentScreen()
                            }
                        )
                    }
                }
            }
        }

    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SicretsantajusanTheme {
        Greeting("Android")
    }
}