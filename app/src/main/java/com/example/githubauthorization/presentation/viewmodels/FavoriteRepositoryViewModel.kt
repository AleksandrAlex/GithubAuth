package com.example.githubauthorization.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubauthorization.data.UserRepository
import com.example.githubauthorization.db.EntityRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteRepositoryViewModel @Inject constructor(private val repository: UserRepository): ViewModel() {

    val favoriteRepositories = repository.getFavoriteRepositories()

    fun removeRepositoryFromDB(item: EntityRepo) = viewModelScope.launch(Dispatchers.IO){
        repository.removeRepositoryFromDB(item = item.id)

    }
}
