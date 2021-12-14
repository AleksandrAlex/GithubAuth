package com.example.githubauthorization.domain

import androidx.lifecycle.*
import com.example.githubauthorization.data.UserRepository
import com.example.githubauthorization.models.UserProfile
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class UserProfileViewModel @Inject constructor(private val repository: UserRepository): ViewModel() {

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
            UserProfileViewModelState.Error("Sorry! User was not identified!")
        }
    }
}

sealed class UserProfileViewModelState {
    class Success(val userProfile: UserProfile?): UserProfileViewModelState()
    class Error(val errorMessage: String): UserProfileViewModelState()
    object Loading : UserProfileViewModelState()
}
