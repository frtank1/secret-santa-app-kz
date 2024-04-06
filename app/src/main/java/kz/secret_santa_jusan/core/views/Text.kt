package kz.secret_santa_jusan.core.views

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kz.secret_santa_jusan.ui.theme.DarkGray
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