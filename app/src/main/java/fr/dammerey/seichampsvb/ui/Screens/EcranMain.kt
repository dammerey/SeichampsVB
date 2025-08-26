package fr.dammerey.seichampsvb.ui.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import fr.dammerey.seichampsvb.R
import fr.dammerey.seichampsvb.viewmodel.EquipeViewModel


@Composable
fun EcranMain(
    equipeViewModel: EquipeViewModel,
    onNavigateToPlanning:()-> Unit,
    onNavigateToListContact:()->Unit
)
{
    Column{
        Spacer(modifier= Modifier.height(2.dp))

        Button(
            onClick = {

                onNavigateToPlanning()
            },
            shape = RoundedCornerShape(2.dp),
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .weight(0.50f)
        )
        {
            Image(
                painter = painterResource(id = R.drawable.planning_deux),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(modifier= Modifier.height(1.dp))

        Button(
            onClick = {

                onNavigateToListContact()
            },
            shape = RoundedCornerShape(2.dp),
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .weight(0.50f),

            )
        {

            Image(
                painter = painterResource(id = R.drawable.contact2),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}





