package fr.dammerey.seichampsvb.ui.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.dammerey.seichampsvb.data.MatchInfo
import fr.dammerey.seichampsvb.ui.theme.SeichampsVBTheme

@Composable
fun MatchRow(match: MatchInfo, isHeader : Boolean = false) {
    Card (
        shape = RectangleShape,
        modifier = Modifier.padding(2.dp)
    ){
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .height(30.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically

        )
        {
            Text(
                text = " ${match.position}",
                modifier = Modifier
                    .width(80.dp)
                    .fillMaxSize(),
                textAlign = TextAlign.Center,
                fontWeight = if(isHeader) FontWeight.Bold else FontWeight.Normal,
                fontSize = if(isHeader) 22.sp else 16.sp

            )
            Text(
                text = " ${match.nomEquipe}",
                modifier = Modifier.width(200.dp),
                textAlign = TextAlign.Center,
                fontWeight = if(isHeader) FontWeight.Bold else FontWeight.Normal,
                fontSize = if(isHeader) 22.sp else 16.sp
            )
            Text(
                text = " ${match.score}",
                modifier = Modifier.width(80.dp),
                textAlign = TextAlign.Center,
                fontWeight = if(isHeader) FontWeight.Bold else FontWeight.Normal,
                fontSize = if(isHeader) 22.sp else 16.sp
            )
        }
    }
}

@Preview
@Composable
fun PreviewMatchRow(){

    SeichampsVBTheme {
        MatchRow(MatchInfo("1","Seichamps","52"),true)
    }
}
@Preview
@Composable
fun PreviewMatchRow2(){

    SeichampsVBTheme {
        MatchRow(MatchInfo("1","Seichamps","52"))
    }
}