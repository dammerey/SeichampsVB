package fr.dammerey.seichampsvb.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.google.gson.Gson
import fr.dammerey.seichampsvb.data.Equipe
import fr.dammerey.seichampsvb.util.AppParams
import fr.dammerey.seichampsvb.util.AppParams.FICHIER_EQUIPE_COPIER
import fr.dammerey.seichampsvb.util.ConfigManager
import java.io.File
import java.io.FileReader
import java.io.FileWriter


class SeichampsRepository : SeichampsRepositoryInterface {

    private val gson = Gson()
    private var equipe1 : Equipe? = null
    private var equipe2 : Equipe? = null
    private var equipeIsLoad by mutableStateOf(false)
    var afficherAvertissementEcrasementFichier by  mutableStateOf(false)

  //verifie si les fichiers json des deux equipes existe sinon les copies de Asset vers le repertoire interne de l'application
    /* TODO : dans le cadre des mises a jour de l'application, s'il n'y a pas de modification dans les fichiers json, il faut :
                - verifier si le fichier existe deja (sinon on copie le fichier de l'asset)
                - s'ils existent deja il faut demander a l'utilisateur si il veut remplacer les fichiers existant ou pas

    */
    fun initEquipes(context: Context){
        var appContext = context.applicationContext
        CopyEquipeFilesToMemory(appContext, ecraseFichierExistant = false)
        if(!afficherAvertissementEcrasementFichier) {
            loadEquipes(appContext)
        }
    }

    fun RemplacerFichiersEtRechargerEquipes(context: Context) {
        var appContext = context.applicationContext
        CopyEquipeFilesToMemory(appContext, ecraseFichierExistant = true)
        loadEquipes(appContext)
        Log.d("Ecran", "Repository :charger fichiers json dans telephone a la demande utilisateur")
    }

    /**
     * Étape 1 : Vérifie l’existence des fichiers et copie depuis les assets si besoin
     */
    private fun CopyEquipeFilesToMemory(context: Context, ecraseFichierExistant: Boolean = false) {
        val fileEquipe1 = File(context.filesDir, "${AppParams.nom_EQUIPE1}.json")
        val fileEquipe2 = File(context.filesDir, "${AppParams.nom_EQUIPE2}.json")

        if(!ecraseFichierExistant && !FICHIER_EQUIPE_COPIER) {

            var needConfirmation = false

            if (fileEquipe1.exists()) {
                needConfirmation = true
            }
            else {
                ConfigManager.copyAssetToInternalStorage(context, "${AppParams.nom_EQUIPE1}.json")
                Log.d("Ecran", "Repository-Fichier ${AppParams.nom_EQUIPE1}.json copié depuis les assets")
            }


            if (fileEquipe2.exists()){
                needConfirmation = true
            }
            else {
                ConfigManager.copyAssetToInternalStorage(context, "${AppParams.nom_EQUIPE2}.json")
                Log.d("Ecran", "Repository - Fichier ${AppParams.nom_EQUIPE2}.json copié depuis les assets")
            }
            afficherAvertissementEcrasementFichier = needConfirmation
        }
        else if(!FICHIER_EQUIPE_COPIER){
            ConfigManager.copyAssetToInternalStorage(context, "${AppParams.nom_EQUIPE1}.json")
            ConfigManager.copyAssetToInternalStorage(context, "${AppParams.nom_EQUIPE2}.json")
            afficherAvertissementEcrasementFichier = false
            Log.d("Ecran", "Repository - Fichiers écrasés depuis les assets")
            FICHIER_EQUIPE_COPIER = true
            ConfigManager.saveConfig(AppParams.getConfig())
        }
    }

    /**
     * Étape 2 : Charge les données JSON en mémoire
     */
    fun loadEquipes(context: Context) {
        val fileEquipe1 = File(context.filesDir, "${AppParams.nom_EQUIPE1}.json")
        val fileEquipe2 = File(context.filesDir, "${AppParams.nom_EQUIPE2}.json")

        try {
            FileReader(fileEquipe1).use { equipe1 = gson.fromJson(it, Equipe::class.java) }
            FileReader(fileEquipe2).use { equipe2 = gson.fromJson(it, Equipe::class.java) }

            equipeIsLoad = true
            Log.d("Ecran", "Repository - Équipes chargées avec succès")
        } catch (e: Exception) {
            Log.e("Ecran", "Repository - Erreur lors de la lecture des fichiers JSON", e)
            Toast.makeText(
                context,
                "Erreur : ${e.message}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
    fun equipeIsLoad() : Boolean = equipeIsLoad

    fun SaveEquipe(equipe: Equipe, context: Context){
        var appContext = context.applicationContext
        val file = File(appContext.filesDir, "${equipe.nom}.json")
        val writer = FileWriter(file)
        gson.toJson(equipe, writer)
        writer.close()
    }

    override fun getEquipe1() : Equipe{
        return equipe1?: throw IllegalStateException("Equipe1 non initialisée")
    }
    override fun getEquipe2() : Equipe{
        return equipe2?: throw IllegalStateException("Equipe2 non initialisée")

    }
    override fun getEquipeByName(nom: String): Equipe?{
        return when (nom) {
            AppParams.nom_EQUIPE1 -> getEquipe1()
            AppParams.nom_EQUIPE2 -> getEquipe2()
            else -> null
        }
    }


}





