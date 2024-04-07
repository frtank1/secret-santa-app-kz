package kz.secret_santa_jusan.core.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kz.secret_santa_jusan.R
import kz.secret_santa_jusan.ui.theme.DarkGray
import kz.secret_santa_jusan.ui.theme.Gray
import kz.secret_santa_jusan.ui.theme.interFamily

@Composable
fun SsText(
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(top = 29.dp),
    text: String = "",
    color: Color = DarkGray,
    fontWeight:FontWeight = FontWeight.Bold,
    textAlign:TextAlign = TextAlign.Center,
    fontSize: TextUnit = TextUnit.Unspecified,
) {
    Text(
        modifier = modifier,
        text = text,
        color = color,
        fontFamily = interFamily,
        fontWeight = fontWeight,
        textAlign = textAlign,
        fontSize = fontSize,
    )
}
@Composable
fun SsTextNormal(
    modifier: Modifier = Modifier,
    text: String = "",
    color: Color = Color.Black,
    fontWeight:FontWeight = FontWeight.Normal,
    textAlign:TextAlign = TextAlign.Center,
    fontSize: TextUnit = TextUnit.Unspecified,
) {
    Text(
        modifier = modifier,
        text = text,
        color = color,
        fontFamily = interFamily,
        fontWeight = fontWeight,
        textAlign = textAlign,
        fontSize = fontSize,
    )
}


@Composable
fun TextWithUnderline(
    modifier:Modifier = Modifier
        .padding(top = 10.dp)
        .fillMaxWidth(),
    onClick: (() -> Unit)? = null,
    color: Color = Gray,
    textFirst: String?,
    textSecond:String
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        textFirst?.let {
            Text(
                text = textFirst,
                color = color,
                fontFamily = interFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 7.sp
            )
        }
        Text(
            modifier = Modifier
                .padding(horizontal = 2.dp)
                .clickable { onClick?.invoke() },
            text = textSecond,
            color = color,
            fontFamily = interFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 7.sp,
            style = TextStyle(textDecoration = TextDecoration.Underline)
        )
    }
}