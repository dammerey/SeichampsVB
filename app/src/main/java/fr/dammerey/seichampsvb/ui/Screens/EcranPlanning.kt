package fr.dammerey.seichampsvb.ui.Screens

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import fr.dammerey.seichampsvb.data.MatchInfo
import fr.dammerey.seichampsvb.util.AppParams
import fr.dammerey.seichampsvb.util.isNetworkAvailable
import fr.dammerey.seichampsvb.viewmodel.EquipeViewModel
import fr.dammerey.seichampsvb.viewmodel.MatchInfoViewModel


@Composable
fun EcranPlanning(equipeViewModel: EquipeViewModel, matchInfoViewModel: MatchInfoViewModel = viewModel())
{
    val equipe = equipeViewModel.equipeSelectionnee
    Log.d("Ecran", "EcranPlanning - Equipe sélectionnée: ${equipe?.nom}")
    val context = LocalContext.current
    val matchList by matchInfoViewModel.matchs.collectAsState()

    LaunchedEffect(Unit) {
        matchInfoViewModel.errorMessage.collect { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
    // Dès que l'équipe change, on recharge les matchs
    LaunchedEffect(equipe?.nom) {        // relance la coroutine de chargement des matchs dès que equipe change.
        equipe?.let {
            if (isNetworkAvailable(context)) {
                matchInfoViewModel.loadMatchs(equipe.nom)
                Log.d("Ecran", "EcranPlanning dans Launche- Equipe sélectionnée: ${equipe.nom}")

            } else {
                Toast.makeText(context,"Pas de connexion internet",Toast.LENGTH_SHORT).show()
            }
        }
    }
    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,

    ){
        Box (modifier = Modifier.weight(1f)){
            MatchListScreen(matchList)
        }

        Button(
            onClick = {
                val urlEq1 = AppParams.URL_EQUIPE1
                val urlEq2 = AppParams.URL_EQUIPE2
                var urlFede = ""
                if(equipe?.nom == AppParams.nom_EQUIPE1){
                    urlFede=urlEq1
                }
                else if(equipe?.nom == AppParams.nom_EQUIPE2){
                    urlFede=urlEq2
                }

                val siteFedeIntent =
                    Intent(Intent.ACTION_VIEW, urlFede.toUri())
                context.startActivity(siteFedeIntent)
                Toast.makeText(context, "Ouverture du site de la Fédération…", Toast.LENGTH_SHORT)
                    .show()

            }
        ){
            Text(
                modifier = Modifier.padding(5.dp),
                text = "Voir le classement sur le site de la FFVB",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun MatchListScreen(matchList: List<MatchInfo>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item{
            MatchRow(MatchInfo("Position","Equipe","Score"),true)
        }
        items(matchList) { match ->
            MatchRow(match)
         }

    }
}

