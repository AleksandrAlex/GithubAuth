package com.example.githubauthorization.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubauthorization.data.UserRepository
import com.example.githubauthorization.models.ResponseRepositories
import kotlinx.coroutines.launch
import retrofit2.Response

class SearchRepositoryViewModel(private val repository: UserRepository): ViewModel() {

    private val _state = MutableLiveData<SearchRepositoryViewModelState>()
    val state: LiveData<SearchRepositoryViewModelState>
        get() = _state


    fun getRepositories(search: String) = viewModelScope.launch {
        _state.postValue(SearchRepositoryViewModelState.Loading)
        val result = repository.getRepositories(search)
        val newState = checkResult(result)
        _state.postValue(newState)
    }

    private fun checkResult(result: Response<ResponseRepositories>): SearchRepositoryViewModelState {
        return if (result.isSuccessful){
            SearchRepositoryViewModelState.Success(result.body())
        } else{
            SearchRepositoryViewModelState.Error("Can't find the repository!")
        }
    }
}

sealed class SearchRepositoryViewModelState{
    class Success(val result: ResponseRepositories?): SearchRepositoryViewModelState()
    object Loading : SearchRepositoryViewModelState()
    class Error(val errorMessage: String): SearchRepositoryViewModelState()
}