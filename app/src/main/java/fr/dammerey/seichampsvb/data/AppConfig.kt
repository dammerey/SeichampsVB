package fr.dammerey.seichampsvb.data

data class AppConfig (
    var saison : String,
    var urlEquipe1 : String,
    var urlEquipe2 : String,
    var nomEquipe1 : String,
    var nomEquipe2 : String,
    var championnatEquipe1 : String,
    var championnatEquipe2 : String,
    var fichierEquipeCopier : Boolean
)
