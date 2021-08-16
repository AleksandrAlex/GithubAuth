package com.example.githubauthorization.presentation

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
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

    private val _state = MutableStateFlow<UiState>(UiState.Empty)
    val state: StateFlow<UiState> = _state


    fun getRepositories(search: String) {
        viewModelScope.launch (Dispatchers.IO){
            _state.value = UiState.Loading
            repository.getRepositories(search).collect {
                    data ->
                runCatching {
                    _state.value = UiState.Success(data)
                }.getOrElse {
                    _state.value = UiState.Error(it.message.toString())
                }
            }
        }
    }
}

sealed class UiState{
    class Success(val data: PagingData<Item>): UiState()
    object Loading : UiState()
    class Error(val errorMessage: String): UiState()
    object Empty : UiState()
}