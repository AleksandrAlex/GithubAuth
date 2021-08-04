package com.example.githubauthorization.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubauthorization.UserRepository
import com.example.githubauthorization.model.UserProfile
import kotlinx.coroutines.launch
import retrofit2.Response

class UserProfileViewModel(private val repository: UserRepository): ViewModel() {

    private val _state = MutableLiveData<UserProfileViewModelState>()
    val state: LiveData<UserProfileViewModelState>
        get() = _state


    fun getUserProfile(userName: String) = viewModelScope.launch {
        _state.postValue(UserProfileViewModelState.Loading)
        val userProfile = repository.getUserProfile(userName)
        val newState = checkUserProfile(userProfile)
        _state.postValue(newState)
    }

    private fun checkUserProfile(userProfile: Response<UserProfile>): UserProfileViewModelState {
        return if (userProfile.isSuccessful) {
            UserProfileViewModelState.Success(userProfile.body())
        } else {
            UserProfileViewModelState.Error("Error!")
        }
    }
}

sealed class UserProfileViewModelState {
    class Success(val userProfile: UserProfile?): UserProfileViewModelState()
    class Error(val errorMessage: String): UserProfileViewModelState()
    object Loading : UserProfileViewModelState()
}