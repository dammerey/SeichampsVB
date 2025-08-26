package fr.dammerey.seichampsvb

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import fr.dammerey.seichampsvb.repository.EquipeViewModelFactory
import fr.dammerey.seichampsvb.repository.SeichampsRepository
import fr.dammerey.seichampsvb.ui.nav.NavGraph
import fr.dammerey.seichampsvb.ui.theme.SeichampsVBTheme
import fr.dammerey.seichampsvb.util.AppParams
import fr.dammerey.seichampsvb.util.AppParams.FICHIER_EQUIPE_COPIER
import fr.dammerey.seichampsvb.util.ConfigManager
import fr.dammerey.seichampsvb.viewmodel.EquipeViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ConfigManager.init(this) //recupere le fichier de config
        setContent {
            SeichampsVBTheme {
                MainScreen()
            }
        }
    }
}


@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun MainScreen() {
    val navController = rememberAnimatedNavController()
    val context = LocalContext.current
    val repository = remember {SeichampsRepository()}
    val factory = remember { EquipeViewModelFactory(repository) }
    val equipeViewModel : EquipeViewModel = viewModel(factory = factory)
    val currentRoute = navController
        .currentBackStackEntryFlow
        .collectAsState(initial = navController.currentBackStackEntry)
        .value?.destination?.route
    val showBars = currentRoute !in listOf("splash","config","modifJoueur","nouveauJoueur")
    LaunchedEffect(Unit) {
        repository.initEquipes(context)
    }
    if(repository.afficherAvertissementEcrasementFichier){
        AlertDialog(
            onDismissRequest = {},
            title = { Text(text = "Attention Fichiers Existants") },
            text = {Text("les fichiers Equipe 1 et Equipe 2 Existent deja sur votre appareil.\n Voulez-vous les ecraser ?\n" +
                    "Les contacts de chaque équipe seront remis pars defaut. \n " +
                    "Si vous aviez fait des modifications, elles seront perdues.")},
            confirmButton = {
                TextButton(onClick = {
                    repository.RemplacerFichiersEtRechargerEquipes(context)
                })
                { Text("Oui") }
            },
            dismissButton = {
                TextButton(onClick = {
                    repository.loadEquipes(context)
                    repository.afficherAvertissementEcrasementFichier = false
                    FICHIER_EQUIPE_COPIER = true
                    ConfigManager.saveConfig(AppParams.getConfig())
               })
                { Text("Non") }
            }
        )
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if(showBars) {
                TopBar(navController, equipeViewModel)
            }
        },
        bottomBar = {
            if(showBars){
                BottompBar(navController,equipeViewModel)
            }
        }
    )
    { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding).fillMaxSize()
        ) {

            NavGraph(
                navController = navController,
                equipeViewModel = equipeViewModel
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavController,equipeViewModel: EquipeViewModel) {
    val context = LocalContext.current
    val activity = context as? Activity
    var clic = remember { 0 }
    TopAppBar(
        title = {

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
            )
            {
                val activity = context as? Activity
               Image(
                    painter = painterResource(id = fr.dammerey.seichampsvb.R.drawable.logo_sechamps),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                        .clickable(
                            onClick = {
                                if(clic==3) {
                                    Toast.makeText(context,
                                        "Plus que 2 clics pour ré-initialiser les equipes",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                               if(clic==5){
                                   Toast.makeText(context,
                                       "Redemarer l'appli pour ré-initialiser les equipes",
                                       Toast.LENGTH_LONG
                                   ).show()
                                   AppParams.FICHIER_EQUIPE_COPIER = false
                                   ConfigManager.saveConfig(AppParams.getConfig())
                                   clic = 0
                               }
                                clic++
                            }
                        ),
                    contentScale = ContentScale.Crop
                )
                var nomEquipe = equipeViewModel.equipeSelectionnee?.nom
                val championnatEquipe = when(nomEquipe){
                    AppParams.nom_EQUIPE1 -> AppParams.CHAMPIONNAT_EQUIPE1
                    AppParams.nom_EQUIPE2 -> AppParams.CHAMPIONNAT_EQUIPE2
                    else -> "?"
                }
                if (nomEquipe != null) {
                    Text(
                        text = "${equipeViewModel.equipeSelectionnee?.nom} - ${AppParams.SAISON}/${AppParams.SAISON.toInt().plus(1).toString()} - ${championnatEquipe}",
                        textAlign = TextAlign.End,
                        style = typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text(
                        text = "Choisi ton équipe",
                        textAlign = TextAlign.End,
                        style = typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimary)
                }
                IconButton(
                    onClick = {
                        activity?.finishAffinity() // ferme toutes les activités liées à ta tâche.
                        //exitProcess(0) // Arrête le processus JVM.
                        android.os.Process.killProcess(android.os.Process.myPid())
                        System.exit(0)
                    }
                )
                {
                    Icon(
                        painter = painterResource(id = R.drawable.exit),
                        modifier = Modifier.size(48.dp),
                        contentDescription = "Quitter l'application",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    )
}

@Composable
fun BottompBar(navController: NavController,equipeViewModel: EquipeViewModel) {
    val context = LocalContext.current
    val activity = context as? Activity
    BottomAppBar(

        containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
        contentColor = MaterialTheme.colorScheme.primary,
        modifier = Modifier.fillMaxWidth()
    )
    {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        )
        {
            IconButton(
                onClick = {
                    if (!navController.popBackStack()) {
                        Log.d(
                            "TAG",
                            "${navController.popBackStack()} peut pas revenir en arriere"
                        )
                    }
                }
            )
            {
                Icon(
                    painter = painterResource(id = R.drawable.outline_arrow_back_24),
                    modifier = Modifier.size(48.dp),
                    contentDescription = null
                )
            }
            IconButton(
                onClick = { navController.navigate("selectionEquipe") }
            )
            {
                Icon(
                    imageVector = Icons.Default.Home,
                    modifier = Modifier.size(48.dp),
                    contentDescription = null
                )
            }
            IconButton(
                onClick = {
                    val webIntent = Intent(
                        Intent.ACTION_VIEW,
                        "https://seichampsvolleyball.sitew.fr/".toUri()
                    )
                    context.startActivity(webIntent)
                    Toast.makeText(
                        context,
                        "Ouverture du site de Seichamps Volley-Ball",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
            {
                Icon(
                    painter = painterResource(id = R.drawable.web),
                    modifier = Modifier.size(48.dp),
                    contentDescription = "Site Web"
                )
            }
            IconButton(
                onClick = {
                    val spondIntent =
                        Intent(Intent.ACTION_VIEW, "https://group.spond.com".toUri())
                    context.startActivity(spondIntent)
                    Toast.makeText(context, "Ouverture de Spond…", Toast.LENGTH_SHORT)
                        .show()
                }
            )
            {
                Icon(
                    painter = painterResource(id = R.drawable.spond),
                    modifier = Modifier.size(35.dp),
                    contentDescription = "lancement Spond"
                )
            }
            IconButton(
                onClick = {
                    navController.navigate("config")
                }
            )
            {
                Icon(
                    imageVector = Icons.Default.Settings,
                    modifier = Modifier.size(48.dp),
                    contentDescription = null
                )
            }
        }
    }

}


@Preview
@Composable
fun PreviewMain(){
    SeichampsVBTheme {
        MainScreen()
    }
}
