package fr.dammerey.seichampsvb.util

import fr.dammerey.seichampsvb.data.AppConfig

object AppParams {
    var SAISON = ""
    var URL_EQUIPE1 = ""
    var URL_EQUIPE2 = ""
    var nom_EQUIPE1 = ""
    var nom_EQUIPE2 = ""
    var CHAMPIONNAT_EQUIPE1 = ""
    var CHAMPIONNAT_EQUIPE2 = ""
    var FICHIER_EQUIPE_COPIER : Boolean = false


    fun updateFromConfig(config: AppConfig) {
        SAISON = config.saison
        URL_EQUIPE1 = config.urlEquipe1
        URL_EQUIPE2 = config.urlEquipe2
        nom_EQUIPE1 = config.nomEquipe1
        nom_EQUIPE2 = config.nomEquipe2
        CHAMPIONNAT_EQUIPE1 = config.championnatEquipe1
        CHAMPIONNAT_EQUIPE2 = config.championnatEquipe2
        FICHIER_EQUIPE_COPIER = config.fichierEquipeCopier
    }

    fun updateConfigFromData(annee : String, championnatEquipe1 : String, championnatEquipe2 : String,)  {
        val anneeSuivante = (annee.toInt().plus(1)).toString()
        SAISON = annee
        URL_EQUIPE1 = "https://www.ffvbbeach.org/ffvbapp/resu/vbspo_calendrier.php?saison=$annee/${anneeSuivante}&codent=PTLO54&poule=${getChampionnat(championnatEquipe1)}"
        URL_EQUIPE2 = "https://www.ffvbbeach.org/ffvbapp/resu/vbspo_calendrier.php?saison=$annee/${anneeSuivante}&codent=PTLO54&poule=${getChampionnat(championnatEquipe2)}"
        CHAMPIONNAT_EQUIPE1 = championnatEquipe1
        CHAMPIONNAT_EQUIPE2 = championnatEquipe2
    }

    fun getChampionnat(equipe : String) : String {
        return when (equipe) {
            "Open 1" -> "OP1"
            "Open 2" -> "OP2"
            "Open 3" -> "OP3"
            "Open 4" -> "OP4"
            "Open 5" -> "OP5"
            else -> ""
        }
    }
    fun getConfig() : AppConfig {
        return AppConfig(SAISON, URL_EQUIPE1, URL_EQUIPE2, nom_EQUIPE1, nom_EQUIPE2, CHAMPIONNAT_EQUIPE1, CHAMPIONNAT_EQUIPE2, FICHIER_EQUIPE_COPIER)
    }
}