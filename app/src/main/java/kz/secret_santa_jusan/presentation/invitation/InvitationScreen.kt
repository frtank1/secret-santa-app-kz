package kz.secret_santa_jusan.presentation.invitation

import IInvitationViewModel
import InvitationViewModel
import android.os.Parcelable
import androidx.compose.foundation.Image
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
import kz.secret_santa_jusan.core.views.SsText
import kz.secret_santa_jusan.core.views.TitleBar
import kz.secret_santa_jusan.presentation.registration.RegistrationScreen
import kz.secret_santa_jusan.ui.theme.BrightOrange
import kz.secret_santa_jusan.ui.theme.DarkGray
import kz.secret_santa_jusan.ui.theme.PaleBlue
import kz.secret_santa_jusan.ui.theme.interFamily

@Parcelize
class InvitationScreen : CoreBaseScreen(), Parcelable {

    @Composable
    override fun Content() {
        val viewModel = getScreenModel<InvitationViewModel>()
        val navigator = LocalNavigator.currentOrThrow

        InvitationContent(viewModel = viewModel)
        viewModel.sendEvent(InvitationEvent.Init)
    }
}

@Composable
fun InvitationContent(viewModel: IInvitationViewModel) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = PaleBlue)) {
        TitleBar()

        Image(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 64.dp),
            painter = painterResource(id = R.drawable.santa01),
            contentDescription = null
        )

        Text(
            text = stringResource(id = (R.string.invitation)),
            color = DarkGray,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            fontSize = 40.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Text(
            text = stringResource(id = R.string.fill_out_the_card),
            color = DarkGray,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
        )

        Button(
            onClick = {
                viewModel.sendEvent(InvitationEvent.GoToForm)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 25.dp)
                .align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(BrightOrange)
        ) {
            Text(
                text = stringResource(id = R.string.create_a_participant_card),
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
        }
    }
}
