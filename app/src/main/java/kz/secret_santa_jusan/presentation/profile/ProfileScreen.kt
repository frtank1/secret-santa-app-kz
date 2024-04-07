package kz.secret_santa_jusan.presentation.profile

import android.os.Parcelable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.parcelize.Parcelize
import kz.secret_santa_jusan.core.base.CoreBaseScreen
import kz.secret_santa_jusan.ui.theme.LightBlue

@Parcelize
class ProfileScreen : CoreBaseScreen(), Parcelable {

    @Composable
    override fun Content() {
        val viewModel = getScreenModel<ProfileViewModel>()
        val navigator = LocalNavigator.currentOrThrow
        val navigationEvent = viewModel.navigationEvent.collectAsStateWithLifecycle().value.getValue()
        when(navigationEvent){
            is NavigationEvent.Default -> {}
            is NavigationEvent.Back -> navigator.pop()
            //is NavigationEvent.AuthRouter -> navigator.push(ScreenRegistry.get(AuthRouter.ProfileScreen()))
        }
        SubscribeError(viewModel)
        ProfileContent(viewModel = viewModel)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileContentPreview() {
    ProfileContent(ProfileViewModelPreview())
}


@Composable
fun ProfileContent(viewModel: IProfileViewModel) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = LightBlue)
        //.verticalScroll(rememberScrollState())
    ){
        when (state) {
            is ProfileState.Default -> {
                Button(onClick = {
                }) {
                    Text("OpenProfile")
                }
                Text(text = "sdasd")
            }
        }
    }

}