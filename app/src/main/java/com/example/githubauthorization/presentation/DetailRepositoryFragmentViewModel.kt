package com.example.githubauthorization.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubauthorization.data.UserRepository
import com.example.githubauthorization.db.EntityRepo
import com.example.githubauthorization.models.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DetailRepositoryFragmentViewModel(private val repositoty: UserRepository): ViewModel(){

    private val _stateStarBtn = MutableLiveData<Boolean>(false)
    val stateStarBtn: LiveData<Boolean> = _stateStarBtn


    suspend fun saveRepository(item: Item) = withContext(Dispatchers.IO){
        repositoty.saveRepository(item)
        _stateStarBtn.postValue(true)
    }

    suspend fun removeRepository(item: Item) = withContext(Dispatchers.IO) {
        repositoty.removeRepositoryFromDB(
            EntityRepo(
                id = item.id,
                description = item.description,
                name = item.name,
                created_at = item.created_at,
                full_name = item.full_name,
                git_url = item.git_url,
                language = item.language,
                repos_url = item.owner.repos_url,
                login = item.owner.login,
                avatar_url = item.owner.avatar_url
            )
        )
        _stateStarBtn.postValue(false)
    }
}


class DetailRepositoryFragmentViewModelFactory @Inject constructor(val repository: UserRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailRepositoryFragmentViewModel(repository) as T
    }
}