package com.example.githubauthorization.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubauthorization.data.UserRepository
import javax.inject.Inject

class FavoriteRepositoryViewModel(private val repository: UserRepository): ViewModel() {

    val favoriteRepositories = repository.getFavoriteRepositories()
}

class FavoriteRepositoryViewModelFactory (val repository: UserRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FavoriteRepositoryViewModel(repository) as T
    }
}