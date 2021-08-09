package com.example.githubauthorization.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubauthorization.data.UserRepository
import com.example.githubauthorization.domain.UserProfileViewModel
import javax.inject.Inject

class UserProfileViewModelFactory @Inject constructor(val repository: UserRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserProfileViewModel(repository) as T
    }
}