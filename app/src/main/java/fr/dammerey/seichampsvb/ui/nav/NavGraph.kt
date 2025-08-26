package fr.dammerey.seichampsvb.ui.nav

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import fr.dammerey.seichampsvb.ui.Screens.EcranConfig
import fr.dammerey.seichampsvb.ui.Screens.EcranDetailJoueur
import fr.dammerey.seichampsvb.ui.Screens.EcranListJoueur
import fr.dammerey.seichampsvb.ui.Screens.EcranMain
import fr.dammerey.seichampsvb.ui.Screens.EcranModifJoueur
import fr.dammerey.seichampsvb.ui.Screens.EcranNouveauJoueur
import fr.dammerey.seichampsvb.ui.Screens.EcranPlanning
import fr.dammerey.seichampsvb.ui.Screens.EcranSelectionEquipe
import fr.dammerey.seichampsvb.ui.Screens.SplashScreen
import fr.dammerey.seichampsvb.viewmodel.EquipeViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavGraph(navController: NavHostController, equipeViewModel: EquipeViewModel) {
    AnimatedNavHost(navController = navController, startDestination = "splash") {

        composable("config",
            enterTransition = {
                fadeIn()+slideInVertically { height -> height }
            },
            exitTransition = {
                fadeOut()+slideOutVertically { height -> height }
            },
        ){
            EcranConfig(navController= navController)

        }
        composable("splash",
            enterTransition = {fadeIn()},
            exitTransition = {fadeOut()}
        ) {
            SplashScreen(navController= navController)
        }
        composable("selectionEquipe",
            enterTransition = {fadeIn()},
            exitTransition = {fadeOut()}
        ) {

            LaunchedEffect(Unit) {
                equipeViewModel.resetSelection()
            }
            EcranSelectionEquipe(
                equipeViewModel = equipeViewModel,
                onclicEquipe = {
                    navController.navigate("main")
                }
            )
        }
        composable("main",
            enterTransition = {fadeIn()},
            exitTransition = {fadeOut()}
        ) {
            EcranMain(
                equipeViewModel = equipeViewModel,

                onNavigateToPlanning = {
                    navController.navigate("planning")
                },

                onNavigateToListContact = {
                    navController.navigate("listJoueur")
                }
            )
        }

        composable ("listJoueur",
            enterTransition = {fadeIn()},
            exitTransition = {fadeOut()}
        ){
            EcranListJoueur(
                equipeViewModel = equipeViewModel,
                onClicItemJoueur = {
                    navController.navigate("detailContact")
                    {
                        popUpTo("listJoueur") {
                            inclusive = true
                        }
                    }
                },
                onNavigateAjouterJoueur = {
                    navController.navigate("nouveauJoueur") {
                        popUpTo("listJoueur") {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable ("detailContact",
            enterTransition = {fadeIn()},
            exitTransition = {fadeOut()}
        ){
            EcranDetailJoueur(
                equipeViewModel = equipeViewModel ,
                onClicModifierJoueur = {
                    navController.navigate("modifJoueur"){
                        popUpTo("detailContact") {
                            inclusive = true
                        }
                    }
                },
                onClicRetourListeJoueur = {
                    navController.navigate("listJoueur") {
                        popUpTo("detailContact") {
                            inclusive = true
                        }
                    }
                },
                onClicSupprimerJoueur = {
                    navController.navigate("listJoueur") {
                        popUpTo("detailContact") {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable ("modifJoueur",
            enterTransition = {fadeIn()+slideInVertically { height -> height }},
            exitTransition = {fadeOut()+slideOutVertically { height -> height }}
        )
        {
            EcranModifJoueur(
                equipeViewModel = equipeViewModel,
                onClicValiderJoueur={

                    navController.navigate("detailContact"){
                        popUpTo("modifJoueur"){ //permert de supprimer l'écran de modification
                            inclusive = true
                        }
                    }
                },
                onClicRetourListeJoueur = {
                    navController.navigate("detailContact") {
                        popUpTo("modifJoueur") {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable ("planning",
            enterTransition = {fadeIn()},
            exitTransition = {fadeOut()}
        ){

            EcranPlanning(equipeViewModel = equipeViewModel)
        }

        composable ("nouveauJoueur",
            enterTransition = {fadeIn()+slideInVertically { height -> height }},
            exitTransition = {fadeOut()+slideOutVertically { height -> height }}
        )
        {
            LaunchedEffect(Unit) {
                equipeViewModel.resetJoueurSelectionner()
            }
            EcranNouveauJoueur(
            equipeViewModel= equipeViewModel,
            onClicCreerJoueur = {
                navController.navigate("listJoueur"){
                    popUpTo("nouveauJoueur"){
                        inclusive = true
                    }
                }
            },
            onClicAnnulerCreerJoueur = {
                navController.navigate("listJoueur"){
                    popUpTo("nouveauJoueur"){
                        inclusive = true
                    }
                }
            }

            )
        }
    }
}
