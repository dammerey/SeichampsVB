package fr.dammerey.seichampsvb.repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import fr.dammerey.seichampsvb.viewmodel.EquipeViewModel

class EquipeViewModelFactory(private val repository: SeichampsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EquipeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EquipeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}