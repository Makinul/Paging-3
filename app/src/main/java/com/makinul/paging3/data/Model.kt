package com.makinul.paging3.data

const val NETWORK_PAGE_SIZE = 25

data class MovieResponse(val page: Int, val results: List<Movie>)

data class Movie(val original_title: String, val poster_path: String, val overview: String)