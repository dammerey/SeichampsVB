package fr.dammerey.seichampsvb.ui.Screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import fr.dammerey.seichampsvb.R
import fr.dammerey.seichampsvb.util.ConfigManager
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    var startAnimation by remember { mutableStateOf(false) }
    var context = LocalContext.current


    val scale by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0.5f,
        animationSpec = tween(1000, easing = FastOutSlowInEasing),
        label = "scaleAnim"
    )

    val alpha by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(1000),
        label = "alphaAnim"
    )
    LaunchedEffect(Unit) {
        ConfigManager.init(context)


    }

    LaunchedEffect(Unit) {
        startAnimation = true
        delay(4000)
        navController.navigate("selectionEquipe") {
            popUpTo("splash") { inclusive = true } // on supprime l’écran splash du backstack
        }
    }

    Box(

        modifier = Modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                shape = MaterialTheme.shapes.medium
            ),
        contentAlignment = Alignment.Center
    )
    {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,



        ){
            Text(
                text = "Seichamps Volley-Ball",
                style = typography.headlineLarge,
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 40.sp,
           )
            Spacer(modifier = Modifier.height(150.dp) )
            Image(
                painter = painterResource(id = R.drawable.logo_sechamps), // mets ici ton image/logo
                contentDescription = "Logo Seichamps VB",
                modifier = Modifier
                    .size(200.dp)
                    .alpha(alpha)
                    .scale(scale)
            )
            Spacer(modifier = Modifier.height(150.dp) )
            Text(
                text = "@Dammerey.fr - 08.2025 - Version 2",
                style = typography.headlineLarge,
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 15.sp,
            )
        }
    }
}