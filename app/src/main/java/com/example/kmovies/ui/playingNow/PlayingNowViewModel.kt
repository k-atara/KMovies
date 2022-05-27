package com.example.kmovies.ui.playingNow

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.kmovies.R
import com.example.kmovies.domain.repository.MovieRepository
import com.example.kmovies.domain.usecase.PlayingMoviesUseCase
import com.example.kmovies.util.MovieUIState
import kotlinx.coroutines.launch

class PlayingNowViewModel @ViewModelInject constructor(
    private val useCase: PlayingMoviesUseCase,
    private val repository: MovieRepository,
    @Assisted state: SavedStateHandle
) : ViewModel() {

    val movieState: MutableLiveData<MovieUIState> = MutableLiveData()
    private val currentQuery = state.getLiveData(CURRENT_QUERY, EMPTY_VALUE)

    init {
        collectMovies()
    }

    internal fun collectMovies() = currentQuery.switchMap { query ->
        if (query.isEmpty()){
            repository.getNowPlayingMovies().cachedIn(viewModelScope)
        }else{
            repository.getSearchMovies(query)
        }
    }

    fun searchMovies(query: String){
        currentQuery.value = query
    }

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

    companion object{
        private const val CURRENT_QUERY = "current_query"
        private const val EMPTY_VALUE = ""
    }
}


