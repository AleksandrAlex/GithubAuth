package com.example.githubauthorization.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.githubauthorization.data.UserRepository
import com.example.githubauthorization.db.EntityRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.switchMap
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FavoriteDetailsRepositoryViewModel @Inject constructor(private val repository: UserRepository) : ViewModel(){


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

class FavoriteDetailsRepositoryViewModelFactory @Inject constructor(val repository: UserRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FavoriteDetailsRepositoryViewModel(repository) as T
    }
}