package com.example.githubauthorization.presentation

import android.annotation.SuppressLint
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.githubauthorization.data.UserRepository
import com.example.githubauthorization.models.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class SearchRepositoryViewModel @Inject constructor(private val repository: UserRepository): ViewModel() {

    private val currentQuery = MutableLiveData<String>()

    val repositories = currentQuery.switchMap {
        repository.getRepositories(it).cachedIn(viewModelScope)
    }


    fun getRepositories(search: String){
        currentQuery.value = search
    }

}

