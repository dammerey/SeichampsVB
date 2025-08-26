package fr.dammerey.seichampsvb.ui.Screens

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.dammerey.seichampsvb.ui.dialogue.ConfirmationDialogue
import fr.dammerey.seichampsvb.viewmodel.EquipeViewModel


@Composable
fun EcranDetailJoueur(
    equipeViewModel: EquipeViewModel,
    onClicModifierJoueur: () -> Unit,
    onClicRetourListeJoueur:()-> Unit,
    onClicSupprimerJoueur:()->Unit){

    val joueur = equipeViewModel.joueurSelectionne.value
    val context = LocalContext.current
    var afficherAvertissementSuppression by remember { mutableStateOf(false) }
    // recharge l'equipe au cas ou il a eu modification
    equipeViewModel.selectEquipe(equipeViewModel.equipeSelectionnee?.nom.toString())

    Column {
        Spacer(modifier = Modifier.height(15.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
                .shadow(15.dp, shape = RoundedCornerShape(5.dp)),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(15.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier
                        .fillMaxWidth()

                        .background(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = RoundedCornerShape(5.dp)
                        )
                        .shadow(elevation = 25.dp, shape = RoundedCornerShape(5.dp))
                        .padding(start = 15.dp)
                )
                {
                    IconButton(
                        onClick = { onClicRetourListeJoueur() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Retour",
                            modifier = Modifier.size(30.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    Row() {
                        IconButton(
                            onClick = { onClicModifierJoueur() }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Modifier",
                                modifier = Modifier.size(30.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }

                        IconButton(
                            onClick = {
                                afficherAvertissementSuppression = true

                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Supprimer",
                                modifier = Modifier.size(30.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "${joueur?.nom}   ${joueur?.prenom}",
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = " Numéro Licence : ",
                    fontSize = 20.sp
                )
                Text(
                    text = "${joueur?.numLicence} ",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(3.dp))
                Icon(
                    imageVector = Icons.Default.Call,
                    contentDescription = "Numero telephone"
                )
                Text(
                    text = "${joueur?.telephone}",
                    modifier = Modifier
                        .clickable {
                            joueur?.telephone?.let { //.let permet d'executer une action si la variable n'est pas null (remplace un if (joueur noon null....
                                val intent = Intent(Intent.ACTION_DIAL).apply {
                                    val numero = joueur.telephone.replace(".", "")
                                    data =
                                        Uri.parse("tel:$numero") // .parse convertit une chaine de caractère en un objet uri
                                }
                                context.startActivity(intent)
                            }
                        }
                        .padding(start = 5.dp),
                    textDecoration = TextDecoration.Underline,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(3.dp))
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Adresse Joueur",
                    modifier = Modifier.padding(end = 5.dp)
                )
                Text(
                    text = "${joueur?.adresse} ",
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .clickable {
                            if (!joueur?.adresse.isNullOrBlank()) {
                                val geoUri = "geo:0,0?q=${Uri.encode(joueur?.adresse)}"
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(geoUri))
                                val chooser = Intent.createChooser(intent, "Ouvrir avec")
                                Log.d("Ecran", "EcranDetailJoueur:Adresse :${joueur?.adresse} ")

                                try {
                                    context.startActivity(chooser)
                                } catch (e: Exception) {
                                    Toast.makeText(
                                        context,
                                        "Aucune application disponible",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            } else Toast.makeText(
                                context,
                                "Pas d'adresse renseignée",
                                Toast.LENGTH_LONG
                            ).show()

                        }
                )
               Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }

    ConfirmationDialogue(
        afficherAvertissementSuppression,
        "Suppression Joueur",
        "Voulez-vous supprimer ce joueur ?",
        onConfirm = {
            afficherAvertissementSuppression = false
            equipeViewModel.SupprimerJoueur(joueur!!, context)
            onClicSupprimerJoueur() },
        onDismiss = { afficherAvertissementSuppression = false }
        )
}









/*
@Preview
@Composable
fun PreviewEcranJoueur(){
    SeichampsVBTheme {
        EcranDetailJoueur(onclicModifierJoueur={})
    }

}*/