package fr.dammerey.seichampsvb.repository

import fr.dammerey.seichampsvb.data.Equipe

interface SeichampsRepositoryInterface {
    fun getEquipe1() : Equipe
    fun getEquipe2() : Equipe
    fun getEquipeByName(nom: String): Equipe?
}