package com.makinul.paging3.data.network.movie

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.makinul.paging3.data.Movie
import com.makinul.paging3.data.network.MovieApiService

class MoviePagingSource(private val apiService: MovieApiService) : PagingSource<Int, Movie>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {

        return try {
            val position = params.key ?: 1
            val response =
                apiService.getTopRatedMovies("e8d648003bd11b5c498674fbd4905525", "en-US", position)
            LoadResult.Page(
                data = response.body()!!.results,
                prevKey = if (position == 1) null else position - 1,
                nextKey = position + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}