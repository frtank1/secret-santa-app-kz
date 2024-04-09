package kz.secret_santa_jusan.core.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
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
import kz.secret_santa_jusan.ui.theme.SicretsantajusanTheme
import kz.secret_santa_jusan.ui.theme.White



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ButtonPreview() {
    SicretsantajusanTheme {
    }
}
@Composable
fun ProfileInfoCadr(
    modifier:Modifier = Modifier
        .fillMaxWidth(),
    onClick: (() -> Unit)? = null,
    onClickeText:(()->Unit)? = null,
    painter: Painter = painterResource(id = R.drawable.santa_claus),
    name:String? = null,
    email:String? = null,
    colors: CardColors = CardDefaults.cardColors(
        containerColor = White
    )
){
    modifier.clickable {
        onClick?.invoke()
    }
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(
            2.dp,
            BrightOrange
        ),
        colors = colors
    ) {
        Row {
            Image(
                painter = painter,
                contentDescription = null,
                colorFilter = ColorFilter.tint(White),
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 8.dp)
                    .size(width = 40.dp, height = 40.dp)
                    .clip(shape = RoundedCornerShape(100.dp))
                    .background(Red)
            )
            Column {
                Text(
                    modifier = Modifier
                        .padding(start = 11.dp,top = 10.dp),
                    text = name?:"Имя Фамилия",
                    color = DarkGray,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    fontSize = 15.sp,
                )

                Text(
                    modifier = Modifier
                        .padding(start = 11.dp)
                        .clickable { onClickeText?.invoke() },
                    text = email?:"example@gmail.com",
                    color = DarkGray,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    fontSize = 10.sp,
                )
            }
        }
    }
}


