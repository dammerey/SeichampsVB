package fr.dammerey.seichampsvb.ui.Screens


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.dammerey.seichampsvb.viewmodel.EquipeViewModel

@Composable
fun EcranListJoueur(equipeViewModel: EquipeViewModel,
                    onClicItemJoueur :() -> Unit,
                    onNavigateAjouterJoueur :() -> Unit){

    val equipe =  equipeViewModel.equipeSelectionnee
    Log.d("Ecran ", "EcranList Joueur: ${equipe?.nom}")
    val listeJoueurs = equipeViewModel.joueursTries
    equipeViewModel.TrierEquipe()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.background(Color.Gray)
        ) {
            items(listeJoueurs?.size ?: 0) { index ->
                val joueurCourant = listeJoueurs?.get(index)
                Button(
                    onClick = {
                        equipeViewModel.joueurSelectionne.value = joueurCourant

                        equipeViewModel.updateChampsJoueurSelectionner()
                        onClicItemJoueur()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(75.dp),
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 30.dp
                    ),
                )
                {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.SpaceAround
                    ) {
                        Text(
                            text = " ${joueurCourant?.nom ?: 0}  ${joueurCourant?.prenom ?: 0}",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Text(
                            text = " Numéro Licence : ${joueurCourant?.numLicence ?: 0}",
                            color = MaterialTheme.colorScheme.secondary

                        )
                    }

                }
                Spacer(modifier = Modifier.height(1.dp))


            }
        }
        FloatingActionButton(
            onClick = {
                onNavigateAjouterJoueur()
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ) {
            Icon(Icons.Default.Add,contentDescription = "Ajouter joueur")
        }
    }
}
/*
@Preview
@Composable
fun PreviewEcranEquipe(){
    SeichampsVBTheme {

        EcranListContact(onClicItemJoueur={})
    }

}*/