package com.example.githubauthorization.api

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.githubauthorization.GitHubApi
import com.example.githubauthorization.db.RepoDB
import com.example.githubauthorization.models.Item
import com.example.githubauthorization.models.ItemHolder
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import javax.inject.Inject

class ItemsRepositoryPagingSource(
    private val db: RepoDB,
    private val api: GitHubApi,
    private val query: String
): PagingSource<Int, ItemHolder>() {


    override fun getRefreshKey(state: PagingState<Int, ItemHolder>): Int? {
        val position = state.anchorPosition?: return null
        val page = state.closestPageToPosition(position) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ItemHolder> {
        val pageNumber = params.key ?: 1
        val pageSize = params.loadSize

        val response = api.getListUserRepository(query, pageNumber, pageSize)
        val repositoriesFromDb = db.repositoryDAO.getRepositoriesFromDB()
        if (response.isSuccessful){
            val items = checkNotNull(response.body()?.items)

            // match response with DB


            val repoFromDb = db.repositoryDAO.getRepositoriesFromDB().first()

            val itemsHolder = items.flatMap { item ->
                    repoFromDb.map {
                        ItemHolder(
                                item = item,
                                isFavorite = item.id == it.id
                        )
                    }
                }



            val nextKey = if (itemsHolder.size < pageSize) null else pageNumber + 1
            val prevKey = if (pageSize == 1) null else pageNumber - 1
            return LoadResult.Page(itemsHolder, prevKey, nextKey)
        } else {
            return LoadResult.Error(HttpException(response))
        }
    }
}