package com.example.githubauthorization.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubauthorization.Repository
import com.example.githubauthorization.data.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteDetailsRepositoryViewModel @Inject constructor(private val repository: Repository) : ViewModel(){


    private val id: MutableStateFlow<Long> = MutableStateFlow(0)

    val entityRepo = id.flatMapLatest {
        repository.getItemFromDatabase(it)
    }

    fun setItem(itemId: Long) {
        id.value = itemId
    }

    suspend fun removeRepositoryFromDatabase(itemId: Long) = viewModelScope.launch {
        repository.removeRepositoryFromDB(itemId)
    }
}
