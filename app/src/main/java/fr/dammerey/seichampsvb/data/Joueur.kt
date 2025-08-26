package fr.dammerey.seichampsvb.data

data class Joueur (var nom: String, var prenom: String, var numLicence: String, var telephone: String, var adresse : String) {

    fun AfficheJoueur(){
        println("**** $nom $prenom ******")
        println("Numero de licence : $numLicence")
        println("Telephone : $telephone")
        println("Adresse : $adresse \n")
    }
}


