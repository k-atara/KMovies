package com.example.kmovies.ui.playingNow

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.kmovies.data.remote.model.MovieResponse
import com.example.kmovies.domain.usecase.PlayingMoviesUseCase

class PlayingNowViewModel @ViewModelInject constructor(
    private val useCase: PlayingMoviesUseCase
) : ViewModel() {

    //internal var movies: LiveData<PagingData<MovieResponse>> = useCase.invoke()

    init {
        collectMovies()
    }

    internal fun collectMovies() = useCase.invoke()
}