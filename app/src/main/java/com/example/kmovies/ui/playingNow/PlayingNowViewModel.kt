package com.example.kmovies.ui.playingNow

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.bumptech.glide.load.engine.Resource
import com.example.kmovies.R
import com.example.kmovies.data.remote.model.MovieDetailResponse
import com.example.kmovies.data.remote.model.MovieResponse
import com.example.kmovies.data.remote.model.MoviesResponse
import com.example.kmovies.domain.repository.MovieRepository
import com.example.kmovies.domain.usecase.PlayingMoviesUseCase
import com.example.kmovies.util.MovieUIState
import kotlinx.coroutines.launch
import retrofit2.Response

class PlayingNowViewModel @ViewModelInject constructor(
    private val useCase: PlayingMoviesUseCase,
    private val repository: MovieRepository
) : ViewModel() {

    val movieState: MutableLiveData<MovieUIState> = MutableLiveData()

    init {
        collectMovies()
    }

    internal fun collectMovies() = useCase.invoke()

    internal fun getMovie(id: Int) = viewModelScope.launch {
        movieState.postValue(MovieUIState.Loading(true))
        val response = repository.getDetail(id)
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                movieState.postValue(MovieUIState.Loading(false))
                movieState.postValue(MovieUIState.Success(resultResponse))
            }
        }else
            movieState.postValue(MovieUIState.Error(R.string.title_error))
    }
}


