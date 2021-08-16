package com.example.githubauthorization.api

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.githubauthorization.GitHubApi
import com.example.githubauthorization.models.Item
import retrofit2.HttpException

class ItemsRepositoryPagingSource(
    private val api: GitHubApi,
    private val query: String
): PagingSource<Int, Item>() {

    override fun getRefreshKey(state: PagingState<Int, Item>): Int? {
        val position = state.anchorPosition?: return null
        val page = state.closestPageToPosition(position) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Item> {
        val pageNumber = params.key ?: 1
        val pageSize = params.loadSize

        val response = api.getListUserRepository(query, pageNumber, pageSize)
        if (response.isSuccessful){
            val items = checkNotNull(response.body()?.items)
            val nextKey = if (items.size < pageSize) null else pageNumber + 1
            val prevKey = if (pageSize == 1) null else pageNumber - 1
            return LoadResult.Page(items, prevKey, nextKey)
        } else {
            return LoadResult.Error(HttpException(response))
        }
    }
}