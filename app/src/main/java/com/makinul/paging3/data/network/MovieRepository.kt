package com.makinul.paging3.data.network

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.makinul.paging3.data.Movie
import com.makinul.paging3.data.NETWORK_PAGE_SIZE
import com.makinul.paging3.data.network.movie.MoviePagingSource

class MovieRepository constructor(private val apiService: MovieApiService) {

    fun getAllMovies(): LiveData<PagingData<Movie>> {

        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = 10
            ),
            pagingSourceFactory = {
                MoviePagingSource(apiService)
            }, initialKey = 1
        ).liveData
    }

}