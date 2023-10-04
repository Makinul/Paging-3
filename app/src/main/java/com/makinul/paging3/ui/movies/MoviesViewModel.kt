package com.makinul.paging3.ui.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.makinul.paging3.data.Movie
import com.makinul.paging3.data.network.MovieRepository


class MoviesViewModel constructor(private val repository: MovieRepository) : ViewModel() {

    val errorMessage = MutableLiveData<String>()

    fun getMovieList(): LiveData<PagingData<Movie>> {
        return repository.getAllMovies().cachedIn(viewModelScope)
    }

}