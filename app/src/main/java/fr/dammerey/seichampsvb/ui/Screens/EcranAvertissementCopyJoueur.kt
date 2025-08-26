package fr.dammerey.seichampsvb.ui.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.dammerey.seichampsvb.ui.theme.SeichampsVBTheme

@Composable

fun EcranAvertissementCopyJoueur () {

    var motDePasse = "@SeichampsVB-2025"
    var saisieMotPasse by remember { mutableStateOf("")}

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.primary),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card (
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
                .shadow(15.dp, shape = RoundedCornerShape(5.dp))

        ){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(5.dp),
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Votre Attention !",
                    style = typography.headlineLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.padding(5.dp))
                Text(
                    text = "Pour charger les équipes pré-remplis de Seichamps Volley-Ball, veuillez " +
                            "saisir le mot de passe ou en faire la demande. Sinon vous pouvez partir" +
                            "avec des équipes vide.",
                    style = typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = saisieMotPasse,
                        onValueChange = {
                            saisieMotPasse = it
                        },
                        label = { Text("Mot de passe") },
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp),
                        singleLine = true,
                        textStyle = typography.titleLarge,

                        supportingText = {
                          /*  if (!EquipeViewModel.PermissionOKChargerEquipe) {
                                Text("Mot de passe incorrect")
                            }*/
                        },
                        //isError = motDePasse != saisieMotPasse,
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)


                    )
                    Button(
                        onClick = {
                          /*  if (motDePasse == saisieMotPasse) {
                                EquipeViewModel.PermissionOKChargerEquipe = true
                            } else {
                                EquipeViewModel.PermissionOKChargerEquipe = false
                            }
                            */
                        }
                    ) {
                        Text(
                            text = "OK",
                            style = typography.titleMedium,
                            modifier = Modifier.width(50.dp)
                                .padding(10.dp),
                            textAlign = TextAlign.Center

                        )
                    }

                }
                Button(
                    onClick = {}
                ) {
                    Text(
                        text = "Création d'équipe vide",
                        style = typography.titleMedium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.padding(5.dp))
                Button(
                    onClick = {}
                ) {
                    Text(
                        text = "Demander le mot de passe",
                        style = typography.titleMedium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        textAlign = TextAlign.Center)
                }
            }
        }
    }



}

@Preview
@Composable
fun PreviewEcranAvertissementCopyJoueur(){

    SeichampsVBTheme {
        EcranAvertissementCopyJoueur()
    }
}
