package kz.secret_santa_jusan.core.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kz.secret_santa_jusan.R
import kz.secret_santa_jusan.ui.theme.BrightOrange
import kz.secret_santa_jusan.ui.theme.DarkGray
import kz.secret_santa_jusan.ui.theme.DeepTeal
import kz.secret_santa_jusan.ui.theme.Gray
import kz.secret_santa_jusan.ui.theme.LightBlue
import kz.secret_santa_jusan.ui.theme.PaleBlue
import kz.secret_santa_jusan.ui.theme.Red
import kz.secret_santa_jusan.ui.theme.White


@Composable
fun GameCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    painter: Painter = painterResource(id = R.drawable.santa_r3),
    title:String?=null,
    count:String?=null,
    own:Boolean = false
) {
    Card(
        modifier = Modifier
            .padding(vertical = 21.dp)
            .fillMaxWidth()
            .clickable {
                onClick?.invoke()
            },
        shape = RoundedCornerShape(32.dp),
        colors = CardDefaults.cardColors(
            containerColor = White
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            SsText(
                modifier = Modifier
                    .padding(top = 17.dp)
                    .padding(horizontal = 65.dp)
                    .fillMaxWidth(),
                text = title?:stringResource(id = R.string.Название),
                color = BrightOrange,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                fontSize = 15.sp,
            )
            Image(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top=10.dp),
                  //  .size(width = 90.dp, height = 90.dp),
                painter = painter,
                contentDescription = null,
            )
            SsText(
                modifier = Modifier
                    .padding(top = 17.dp)
                    .padding(horizontal = 91.dp)
                    .fillMaxWidth(),
                text = if(own)stringResource(id = R.string.вы_участников) else stringResource(id = R.string.Вы_Организатор),
                color = Gray,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                fontSize = 10.sp,
            )
            SsText(
                modifier = Modifier
                    .padding(horizontal = 57.dp)
                    .padding(bottom = 12.dp)
                    .fillMaxWidth(),
                text = stringResource(id = R.string.участники) + count?:stringResource(id = R.string.колличество_участников) ,
                color = Gray,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                fontSize = 10.sp,
            )
        }
    }
}


