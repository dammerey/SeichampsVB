package fr.dammerey.seichampsvb.ui.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.dammerey.seichampsvb.data.Joueur
import fr.dammerey.seichampsvb.util.capitalizeFirst
import fr.dammerey.seichampsvb.viewmodel.EquipeViewModel


@Composable
fun EcranModifJoueur(equipeViewModel: EquipeViewModel,
                     onClicValiderJoueur:()->Unit,
                     onClicRetourListeJoueur:() -> Unit){

    val joueur = equipeViewModel.joueurSelectionne.value
    val context = LocalContext.current

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        horizontalAlignment = Alignment.CenterHorizontally,

    ){
        Spacer(modifier = Modifier.height(50.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
                .shadow(15.dp, shape = RoundedCornerShape(5.dp))
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = RoundedCornerShape(5.dp)
                        )
                        .shadow(elevation = 20.dp, shape = RoundedCornerShape(10.dp))
                ){
                    Text(
                        text = "MODIFICATION DU JOUEUR ",
                        style = typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }
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
                Spacer(modifier = Modifier.height(5.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = equipeViewModel.saisieNom,
                        onValueChange = { equipeViewModel.saisieNom = it.capitalizeFirst() },
                        label = { Text("Nom ") },
                        modifier = Modifier.weight(1f),
                        textStyle = typography.titleLarge
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    OutlinedTextField(
                        value = equipeViewModel.saisiePrenom,
                        onValueChange = { equipeViewModel.saisiePrenom = it.capitalizeFirst() },
                        label = { Text("Prenom ") },
                        modifier = Modifier.weight(1f),
                        textStyle = typography.titleLarge
                    )
                }
                Spacer(modifier = Modifier.height(5.dp))
                OutlinedTextField(
                    value = equipeViewModel.saisieTelephone,
                    onValueChange = {input ->
                        if(input.all{it.isDigit() || it == '.'} && input.count{it.isDigit()}<=10){
                            equipeViewModel.saisieTelephone = input
                        }
                    },
                    label = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Call,
                                contentDescription = "Numéro telephone",
                                modifier = Modifier
                                    .padding(end = 5.dp)
                                    .size(15.dp)
                            )
                            Text("Téléphone ")
                        }
                    },
                    textStyle = typography.titleLarge,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                )

                Spacer(modifier = Modifier.height(3.dp))

                OutlinedTextField(
                    value = equipeViewModel.saisieAdresse,
                    onValueChange = {
                        equipeViewModel.saisieAdresse = it.toString()
                    },
                    label = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Home,
                                contentDescription = "Adresse Joueur",
                                modifier = Modifier
                                    .padding(end = 5.dp)
                                    .size(15.dp)
                            )
                            Text("Adresse ")
                        }
                    },
                    textStyle = typography.titleLarge,
                    maxLines = 3,
                    minLines = 3
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Button(

                    onClick = {
                        var joueur = Joueur(
                            equipeViewModel.saisieNom,
                            equipeViewModel.saisiePrenom,
                            equipeViewModel.saisieNumLicence,
                            equipeViewModel.saisieTelephone,
                            equipeViewModel.saisieAdresse
                        )
                        equipeViewModel.ModifierJoueur(joueur, context)
                        equipeViewModel.updateJoueurSelectionner()
                        onClicValiderJoueur()
                    },
                    elevation= ButtonDefaults.buttonElevation(
                        defaultElevation = 10.dp
                    )
                )
                {
                    Text(
                        text = "Valider",
                        style = typography.titleLarge,
                        modifier = Modifier.width(80.dp),
                        textAlign = TextAlign.Center
                    )
                }
                Button(
                    onClick = {
                        onClicRetourListeJoueur()
                    },
                    elevation= ButtonDefaults.buttonElevation(
                        defaultElevation = 10.dp
                    )
                )
                {
                    Text(
                        text = "Annuler",
                        style = typography.titleLarge,
                        modifier = Modifier.width(80.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

/*
@Preview
@Composable
fun PreviewEcranJoueur(){
    SeichampsVBTheme {
        EcranModifJoueur()
    }

}*/