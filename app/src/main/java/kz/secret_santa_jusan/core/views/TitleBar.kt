package kz.secret_santa_jusan.core.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kz.secret_santa_jusan.R
import kz.secret_santa_jusan.ui.theme.DarkGray
import kz.secret_santa_jusan.ui.theme.Red
import kz.secret_santa_jusan.ui.theme.White


@Composable
fun TitleBar(
    onClickBack: (() -> Unit)? = null,
    actionButton1IconResId: Int? = null,
    actionButton1ClickListener: (() -> Unit)? = null,
    actionButton2IconResId: Int? = null,
    actionButton2ClickListener: (() -> Unit)? = null,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(White)
            .padding(top = 8.dp),
    ) {
        if (onClickBack != null) {
            Icon(
                modifier = Modifier
                    .clickable {
                        onClickBack.invoke()
                    }
                    .padding(start = 12.dp),
                painter = painterResource(id = R.drawable.arrow_back),
                contentDescription = "back",
                tint = DarkGray,

            )
            Image(
                painter = painterResource(id = R.drawable.santa_claus),
                contentDescription = null,
                colorFilter = ColorFilter.tint(White),
                modifier = Modifier
                    .padding(start = 10.dp)
                    .clip(shape = RoundedCornerShape(100.dp))
                    .background(Red)

            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.santa_claus),
                contentDescription = null,
                colorFilter = ColorFilter.tint(White),
                modifier = Modifier
                    .padding(start = 22.dp)
                    .clip(shape = RoundedCornerShape(100.dp))
                    .background(Red)

            )
        }
        if (actionButton1ClickListener != null && actionButton1IconResId != null) {
            Icon(
                painter = painterResource(id = actionButton1IconResId),
                contentDescription = "back",
                tint = DarkGray,
                modifier = Modifier
                    .clickable {
                        actionButton1ClickListener.invoke()
                    }
                    .padding(10.dp)
            )
        }
        if (actionButton2ClickListener != null && actionButton2IconResId != null) {
            Icon(
                painter = painterResource(id = actionButton2IconResId),
                contentDescription = "back",
                tint = DarkGray,
                modifier = Modifier
                    .clickable {
                        actionButton2ClickListener.invoke()
                    }
                    .padding(10.dp)
            )
        }
    }
}
