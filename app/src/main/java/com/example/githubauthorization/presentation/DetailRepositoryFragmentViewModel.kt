package com.example.githubauthorization.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubauthorization.data.UserRepository
import com.example.githubauthorization.db.EntityRepo
import com.example.githubauthorization.models.Item
import com.example.githubauthorization.models.ItemHolder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DetailRepositoryFragmentViewModel @Inject constructor(private val repositoty: UserRepository): ViewModel(){




    suspend fun saveRepository(item: ItemHolder) = withContext(Dispatchers.IO){
        repositoty.saveRepository(item)
    }

    suspend fun removeRepository(item: ItemHolder) = withContext(Dispatchers.IO) {
        repositoty.removeRepositoryFromDB(
            item.item.id
        )
    }
}


class DetailRepositoryFragmentViewModelFactory @Inject constructor(val repository: UserRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailRepositoryFragmentViewModel(repository) as T
    }
}