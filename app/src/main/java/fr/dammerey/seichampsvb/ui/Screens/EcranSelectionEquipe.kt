package fr.dammerey.seichampsvb.ui.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.dammerey.seichampsvb.R
import fr.dammerey.seichampsvb.ui.theme.SeichampsVBTheme
import fr.dammerey.seichampsvb.util.AppParams
import fr.dammerey.seichampsvb.viewmodel.EquipeViewModel

@Composable
fun EcranSelectionEquipe(
    equipeViewModel: EquipeViewModel,
    onclicEquipe : ()-> Unit
)
{
    Column{
        Spacer(modifier= Modifier.height(2.dp))

        Button(
            onClick = {

                equipeViewModel.selectEquipe(AppParams.nom_EQUIPE1)
                onclicEquipe()
                      },
            shape = RoundedCornerShape(2.dp),
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .weight(0.50f),
            contentPadding = PaddingValues(0.dp)
        )
        {
            Box {

                Image(
                    painter = painterResource(id = R.drawable.logo_equipe_une_b),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Text(
                    text = AppParams.nom_EQUIPE1,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(start = 5.dp)
                )

            }
         }

        Spacer(modifier= Modifier.height(1.dp))

        Button(
            onClick = {
                equipeViewModel.selectEquipe(AppParams.nom_EQUIPE2)
                onclicEquipe()
            },
            shape = RoundedCornerShape(2.dp),
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .weight(0.50f),
            contentPadding = PaddingValues(0.dp)

        )
        {
            Box {

                Image(
                    painter = painterResource(id = R.drawable.equipe2_b),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Text(
                    text = AppParams.nom_EQUIPE2,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(start = 5.dp)
                )
            }
        }
    }

}




@Preview
@Composable
fun PreviewSelectionEquipe(){
    SeichampsVBTheme {
        //SelectionEquipe(navController= NavController(it) )
    }

}