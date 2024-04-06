package kz.secret_santa_jusan.core.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kz.secret_santa_jusan.ui.theme.White


@Composable
fun GameCard(
    modifier: Modifier = Modifier,
    onClick: (() ->Unit)? =null,
){
    Card(
        modifier = Modifier
            .padding(vertical = 21.dp)
            .fillMaxWidth()
            .clickable {
                onClick?.invoke()
            },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = White
        )
    ){

    }
}