package fr.dammerey.seichampsvb.data


data class Equipe(var nom: String){

    var listeJoueurs : MutableMap<String, Joueur> = mutableMapOf()

 /*   fun joueurExiste(joueur : Joueur): Boolean{
        return listeJoueurs.containsKey(joueur.numLicence)
    }
    fun getEquipeJoueurs(): List<Joueur> {
        return listeJoueurs.values.toList() // copie pour éviter modification externe
    }

    fun getJoueurByLicence(numLicence: String): Joueur{
        return listeJoueurs[numLicence] ?: throw NoSuchElementException("Joueur non trouvé avec le numero de licence $numLicence")
    }

    fun trierEquipe() {
        // On trie les joueurs par nom
        val joueursTries = listeJoueurs.toList().sortedBy { it.second.nom }
        listeJoueurs = LinkedHashMap<String, Joueur>().apply {
            joueursTries.forEach { (key, joueur) -> put(key, joueur) }
        }
    }
    fun afficherEquipe(){

        println("Equipe $nom ********")
        for ((_,joueur) in listeJoueurs){
            println("${joueur.nom} ${joueur.prenom}")
            println("Numero de licence : ${joueur.numLicence}")
            println("Telephone : ${joueur.telephone}")
            println("Adresse : ${joueur.adresse} \n")
            println("**************************************************")
        }

    }
*/


}
/*
fun main(){

    var joueur1 : Joueur = Joueur("Dammerey","Maxime","2545256","06.36.23.25.32","10 allee des bouleaux")
    var joueur2 : Joueur = Joueur("Campaner","Frederique","2545255","06.25.24.12.25","1 rue du roseaux")
    var joueur3 : Joueur = Joueur("Hoche","Bruno","2545254","06.36.23.25.32","103 boulevard Jean Joresse")
    var joueur4 : Joueur = Joueur("OTH","Sophie","254525","06.36.23.23.01","125 rue du marechal Bigeard")

    var listeJoueur : MutableList<Joueur> = mutableListOf(joueur1,joueur2,joueur3,joueur4)


    var equipe1 : Equipe = Equipe("Equipe 1")
    equipe1.ajouterJoueur(joueur1)
    equipe1.ajouterJoueur(joueur2)
    equipe1.ajouterJoueur(joueur3)

    equipe1.ajouterJoueur(joueur4)

    equipe1.afficherEquipe()

    println("****** Ajouter un joueur Gabriel **********")
    equipe1.ajouterJoueur(
        Joueur("Dammerey","Gabriel","25145254","06.45.26.25.32","10 allee des Bouleaux")
    )
    equipe1.afficherEquipe()

    var chercheJoueur : Joueur
    println("****** Recherche d'un joueur **********")
    chercheJoueur =equipe1.chercherJoueur("dammerey","Maxime")
    if(chercheJoueur.Nom != "") {
        println("joueur trouvé")
        chercheJoueur.AfficheJoueur()
        chercheJoueur.telephone = "01.23.36.53.62"
        equipe1.modifierJoueur(chercheJoueur)
        println("Joueur modifié")
        chercheJoueur.AfficheJoueur()

    }

}*/