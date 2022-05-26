package com.example.kmovies.util

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.kmovies.data.remote.model.MovieDetailResponse
import com.example.kmovies.data.remote.model.MovieResponse

sealed class MovieUIState{
    data class Loading(var loading: Boolean) : MovieUIState()
    data class Success(var movie: MovieDetailResponse) : MovieUIState()
    data class SuccessListMovies(var movies: PagingData<MovieResponse>) : MovieUIState()
    data class Error(var error: Int) : MovieUIState()
}