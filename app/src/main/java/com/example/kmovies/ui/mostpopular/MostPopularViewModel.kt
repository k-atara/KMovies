package com.example.kmovies.ui.mostpopular

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kmovies.R
import com.example.kmovies.domain.repository.MovieRepository
import com.example.kmovies.domain.usecase.MostPopularUseCase
import com.example.kmovies.domain.usecase.PlayingMoviesUseCase
import com.example.kmovies.util.MovieUIState
import kotlinx.coroutines.launch

class MostPopularViewModel @ViewModelInject constructor(
    private val useCase: MostPopularUseCase,
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