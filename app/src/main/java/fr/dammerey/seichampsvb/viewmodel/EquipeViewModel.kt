package fr.dammerey.seichampsvb.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import fr.dammerey.seichampsvb.data.Equipe
import fr.dammerey.seichampsvb.data.Joueur
import fr.dammerey.seichampsvb.repository.SeichampsRepository


class EquipeViewModel (private val repository: SeichampsRepository): ViewModel() {

    var equipeSelectionnee by mutableStateOf<Equipe?>(null)
        private set
    var joueurSelectionne = mutableStateOf<Joueur?>(null)  // mutableStateOf permet de rendre observable les changement dans Equipe et de recomposer
    // la variable a deux etat soit null (?) soit Equipe
    var joueursTries by mutableStateOf<List<Joueur>>(emptyList())
        private set
    var saisieNom by mutableStateOf("")
    var saisiePrenom by mutableStateOf("")
    var saisieNumLicence by mutableStateOf("")
    var saisieTelephone by mutableStateOf("")
    var saisieAdresse by mutableStateOf("")
    var erreur by mutableStateOf(false)



    fun updateChampsJoueurSelectionner(){
        saisieNom = joueurSelectionne.value?.nom ?: ""
        saisiePrenom = joueurSelectionne.value?.prenom ?: ""
        saisieNumLicence = joueurSelectionne.value?.numLicence ?: ""
        saisieTelephone = joueurSelectionne.value?.telephone ?: ""
        saisieAdresse = joueurSelectionne.value?.adresse ?: ""
    }
    fun updateJoueurSelectionner(){
        joueurSelectionne.value?.nom = saisieNom
        joueurSelectionne.value?.prenom = saisiePrenom
        joueurSelectionne.value?.numLicence = saisieNumLicence
        joueurSelectionne.value?.telephone = saisieTelephone
        joueurSelectionne.value?.adresse = saisieAdresse
    }

    fun selectEquipe(nomEquipe: String) {
        // Recherche ou crée l'équipe à partir du nom
        if(repository.equipeIsLoad()) {
            val equipe = repository.getEquipeByName(nomEquipe)
            if (equipe != null) {
                Log.d("Ecran", "EquipeViewModel - Equipe sélectionnée: ${equipe?.nom}")
                equipeSelectionnee = equipe
                // Trie la liste des joueurs et la stocke dans joueursTries
                val joueursTriesSorted = equipe.listeJoueurs.values.sortedBy { it.nom }
                joueursTries = joueursTriesSorted
            } else {
                Log.d("Ecran", "EquipeViewModel - Equipe non trouvée")
            }
        }
    }
    fun TrierEquipe() {
        equipeSelectionnee?.let { equipe ->
            val joueurs = equipe.listeJoueurs.values.sortedBy { it.nom.lowercase() }
            joueursTries = joueurs // Met à jour l'état trié
            Log.d("ECRAN ViewModelEquipe", "Liste triée mise à jour : ${joueurs.map { it.nom }}")
        }
    }

     fun resetJoueurSelectionner() {
        joueurSelectionne.value = null
        saisieNom = ""
        saisiePrenom = ""
        saisieNumLicence = ""
        saisieTelephone = ""
        saisieAdresse = ""


    }
    fun resetSelection() {
        equipeSelectionnee = null
    }

    fun SupprimerJoueur(joueur:Joueur, context: Context){
        equipeSelectionnee?.listeJoueurs?.remove(joueur.numLicence)
        TrierEquipe()
        repository.SaveEquipe(equipeSelectionnee!!, context) // !! signifie que l'on est sur que equipeSelectionnee n'est pas null
    }


    fun AjouterJoueur (joueur:Joueur, context: Context){
        val dejaExistant = equipeSelectionnee?.listeJoueurs?.containsKey(joueur.numLicence) == true
        if(!dejaExistant) {
            equipeSelectionnee?.listeJoueurs?.put(joueur.numLicence, joueur)
            TrierEquipe()
            repository.SaveEquipe(equipeSelectionnee!!, context)
        }else {
            Toast.makeText(
                context,
                "la licence N°${joueur.numLicence} du joueur existe déjà",
                Toast.LENGTH_LONG
            ).show()

        }
    }

    fun ModifierJoueur (joueur:Joueur, context: Context){
        equipeSelectionnee?.listeJoueurs?.put(joueur.numLicence,joueur)
        TrierEquipe()
        repository.SaveEquipe(equipeSelectionnee!!, context)
    }
}