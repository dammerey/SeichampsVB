package fr.dammerey.seichampsvb.ui.Screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import fr.dammerey.seichampsvb.R
import fr.dammerey.seichampsvb.util.AppParams
import fr.dammerey.seichampsvb.util.ConfigManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EcranConfig(navController: NavController){

    var selectedChampionnatEquipe1 by remember { mutableStateOf(AppParams.CHAMPIONNAT_EQUIPE1) }
    var selectedChampionnatEquipe2 by remember { mutableStateOf(AppParams.CHAMPIONNAT_EQUIPE2) }
    var menuExpanded1 by remember { mutableStateOf(false) }
    var menuExpanded2 by remember { mutableStateOf(false) }
    val championnat = listOf("Open 1","Open 2","Open 3","Open 4","Open 5")
    var anneeSaison by remember { mutableStateOf(AppParams.SAISON) } // mettre la saion courante

    Column  (
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        horizontalAlignment = Alignment.CenterHorizontally
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
                verticalArrangement = Arrangement.spacedBy(5.dp),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row (horizontalArrangement = Arrangement.Center,
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
                        text = "CONFIGURATION DE LA SAISON",
                        style = typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }

                HorizontalDivider(thickness = 2.dp)
                Spacer(modifier = Modifier.padding(5.dp))
                Text(
                    text = "Saison",
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    style = typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.padding(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Row(

                        verticalAlignment = Alignment.CenterVertically,

                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.plus),
                            contentDescription = "Ajouter une année",
                            //contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(35.dp)
                                .clip(shape = CircleShape)
                                .clickable(
                                    onClick = {
                                        anneeSaison = anneeSaison.toInt().plus(1).toString()
                                    }
                                )
                        )
                        Spacer(modifier = Modifier.padding(3.dp))

                        OutlinedTextField(
                            value = anneeSaison.toString(),
                            onValueChange = { newValue ->
                                newValue.toIntOrNull()?.let {
                                    anneeSaison = it.toString()

                                }
                            },
                            modifier = Modifier
                                .width(80.dp)
                                .height(55.dp),
                            textStyle = typography.titleLarge,
                            singleLine = true,
                            readOnly = true
                        )
                        Spacer(modifier = Modifier.padding(3.dp))
                        Image(
                            painter = painterResource(id = R.drawable.moins),
                            contentDescription = "Ajouter une année",
                            //contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .size(35.dp)
                                .clip(shape = CircleShape)
                                .clickable(
                                    onClick = {
                                        anneeSaison = anneeSaison.toInt().minus(1).toString()
                                    }
                                )
                        )
                        }

                    OutlinedTextField(
                        value = (anneeSaison.toInt().plus(1)).toString(),
                        onValueChange = {},
                        modifier = Modifier
                            .width(80.dp)
                            .height(55.dp),
                        textStyle = typography.titleLarge,
                        singleLine = true,
                        readOnly = true,
                    )
                }
                Spacer(modifier = Modifier.padding(10.dp))
                HorizontalDivider(thickness = 2.dp)
                /******************************************************************************/
                Spacer(modifier = Modifier.padding(5.dp))
                Text(
                    text = "Championnat Open de l' ${AppParams.nom_EQUIPE1}",
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(10.dp),
                    style = typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary

                    )
                Spacer(modifier = Modifier.padding(5.dp))
                ExposedDropdownMenuBox(
                    expanded = menuExpanded1, // booleen qui permet de savoir si le menu est ouvert ou non
                    onExpandedChange = { menuExpanded1 = !menuExpanded1 }
                ) {
                    OutlinedTextField(
                        value = selectedChampionnatEquipe1,
                        onValueChange = {},
                        readOnly = true,
                        //label = { Text("Choisis le championnat") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = menuExpanded1)
                        },
                        colors = ExposedDropdownMenuDefaults.textFieldColors(),
                        modifier = Modifier
                            .menuAnchor()
                            .width(200.dp),
                        textStyle = typography.titleLarge
                    )
                    ExposedDropdownMenu(
                        expanded = menuExpanded1,
                        onDismissRequest = {
                            menuExpanded1 = false
                        } //si on clic en dehors du menu, il se ferme
                    ) {
                        championnat.forEach { itemChampionnat ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        itemChampionnat,
                                        style = typography.titleLarge,
                                    )
                                },
                                onClick = {
                                    selectedChampionnatEquipe1 = itemChampionnat
                                    menuExpanded1 = false
                                }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.padding(10.dp))
                HorizontalDivider(thickness = 2.dp)
                Spacer(modifier = Modifier.padding(5.dp))
                // ******************** EQUIPE 2 ***************************************
                Text(
                    text = "Championnat Open de l'${AppParams.nom_EQUIPE2}",
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(10.dp),
                    style = typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                    )
                Spacer(modifier = Modifier.padding(5.dp))
                ExposedDropdownMenuBox(
                    expanded = menuExpanded2, // booleen qui permet de savoir si le menu est ouvert ou non
                    onExpandedChange = { menuExpanded2 = !menuExpanded2 }
                ) {
                    OutlinedTextField(
                        value = selectedChampionnatEquipe2,
                        onValueChange = {},
                        readOnly = true,
                        //label = { Text("Choisis le championnat") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = menuExpanded2)
                        },
                        colors = ExposedDropdownMenuDefaults.textFieldColors(),
                        modifier = Modifier
                            .menuAnchor()
                            .width(200.dp),
                        textStyle = typography.titleLarge
                    )
                    ExposedDropdownMenu(
                        expanded = menuExpanded2,
                        onDismissRequest = {
                            menuExpanded2 = false
                        } //si on clic en dehors du menu, il se ferme
                    ) {
                        championnat.forEach { itemChampionnat ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        itemChampionnat,
                                        style = typography.titleLarge,
                                    )
                                },
                                onClick = {
                                    selectedChampionnatEquipe2 = itemChampionnat
                                    menuExpanded2 = false
                                }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.padding(10.dp))
                HorizontalDivider(thickness = 2.dp)
                Spacer(modifier = Modifier.padding(10.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Button(
                        onClick = {
                            AppParams.updateConfigFromData(
                                anneeSaison.toString(),
                                selectedChampionnatEquipe1,
                                selectedChampionnatEquipe2
                            )
                            Log.d(
                                "Ecran",
                                "EcranConfig: année $anneeSaison equipe1 $selectedChampionnatEquipe1 equipe2 $selectedChampionnatEquipe2"
                            )
                            ConfigManager.saveConfig(AppParams.getConfig())
                            navController.popBackStack()
                        },
                        elevation= ButtonDefaults.buttonElevation(
                            defaultElevation = 10.dp
                        )
                    ) {
                        Text(
                            text = "Valider",
                            style = typography.titleLarge,
                            modifier = Modifier.width(80.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                    Button(
                        onClick = {
                            navController.popBackStack()

                        },
                        elevation= ButtonDefaults.buttonElevation(
                            defaultElevation = 10.dp
                        )
                    ) {
                        Text(
                            text = "Annuler",
                            style = typography.titleLarge,
                            modifier = Modifier.width(80.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }
                Spacer(modifier = Modifier.padding(10.dp))
            }
        }
    }
}


/*
@Preview
@Composable
fun PreviewEcranConfig(){
    SeichampsVBTheme {
        EcranConfig()
    }
}*/