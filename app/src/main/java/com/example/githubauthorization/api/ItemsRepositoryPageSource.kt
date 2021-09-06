package com.example.githubauthorization.api

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.githubauthorization.GitHubApi
import com.example.githubauthorization.data.UserRepository
import com.example.githubauthorization.db.RepoDB
//import com.example.githubauthorization.models.Item
import com.example.githubauthorization.models.ItemHolder
import com.example.githubauthorization.models.OwnerHolder
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import javax.inject.Inject

class ItemsRepositoryPagingSource(
    private val api: GitHubApi,
    private val db: RepoDB,
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


              val itemsHolder = items.map { item ->

                    ItemHolder(
                        created_at = item.created_at,
                        description = item.description,
                        downloads_url = item.downloads_url,
                        full_name = item.full_name,
                        git_url = item.git_url,
                        id = item.id,
                        name = item.name,
                        owner =
                        OwnerHolder(
                            avatar_url = item.owner.avatar_url,
                            html_url = item.owner.html_url,
                            id = item.owner.id,
                            login = item.owner.login,
                            repos_url = item.owner.repos_url,
                            url = item.owner.url
                        ),
                        pushed_at = item.pushed_at,
                        size = item.size
//                        isFavorite = ????
                    )
            }






            val nextKey = if (itemsHolder.size < pageSize) null else pageNumber + 1
            val prevKey = if (pageSize == 1) null else pageNumber - 1
            return LoadResult.Page(itemsHolder, prevKey, nextKey)
        } else {
            return LoadResult.Error(HttpException(response))
        }
    }
}