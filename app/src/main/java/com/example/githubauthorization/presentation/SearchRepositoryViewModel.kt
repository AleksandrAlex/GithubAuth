package com.example.githubauthorization.presentation


import androidx.lifecycle.*
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.githubauthorization.data.UserRepository
import com.example.githubauthorization.models.ItemHolder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchRepositoryViewModel @Inject constructor(private val repository: UserRepository): ViewModel() {

    private val currentQuery = MutableLiveData<String>()

    val repositories = currentQuery.switchMap {
        repository.getRepositories(it).cachedIn(viewModelScope)
    }


    fun getRepositories(search: String){
        currentQuery.value = search
    }

    fun saveRepository(itemHolder: ItemHolder) = viewModelScope.launch(Dispatchers.IO){
        itemHolder.isFavorite = true
        repository.saveRepository(itemHolder)
    }

    fun removeFromFavorites(itemHolder: ItemHolder) = viewModelScope.launch(Dispatchers.IO){
        itemHolder.isFavorite = false
        repository.removeRepositoryFromDB(itemHolder.item.id)
    }

}

