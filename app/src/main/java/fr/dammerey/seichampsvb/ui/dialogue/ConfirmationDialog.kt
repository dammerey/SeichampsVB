package fr.dammerey.seichampsvb.ui.dialogue

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp

@Composable
fun ConfirmationDialogue(
    show : Boolean,
    title : String,
    message : String,
    onConfirm : () -> Unit,
    onDismiss : () -> Unit

){
    if(show){
        AlertDialog(
            onDismissRequest = onDismiss,
                title = {
                    Text(
                        text = title,
                        color = MaterialTheme.colorScheme.primary
                )
                        },
                text = {
                    Text(
                        text = message,
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.primary,
                        )
                   },
            confirmButton = {
                TextButton(
                    onClick = {
                        onConfirm()
                    }
                ) {
                    Text(
                        text = "Oui",
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 15.sp

                )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = onDismiss
                ) {
                    Text(
                        text = "Annuler",
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        )
    }
}