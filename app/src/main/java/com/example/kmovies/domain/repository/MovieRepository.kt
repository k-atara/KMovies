package com.example.kmovies.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.kmovies.data.remote.MoviePagingSource
import com.example.kmovies.data.remote.model.MovieDetailResponse
import com.example.kmovies.data.remote.service.MovieService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(private val movieApi: MovieService){
    fun getNowPlayingMovies() =
        Pager(
            config = PagingConfig(
                pageSize = 5,
                maxSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MoviePagingSource(movieApi,null) }
        ).liveData

    fun getMostPopularMovies() =
        Pager(
            config = PagingConfig(
                pageSize = 5,
                maxSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MoviePagingSource(movieApi,null, true) }
        ).liveData

    suspend fun getDetail(movieId: Int): Response<MovieDetailResponse> = withContext(
        Dispatchers.IO) {
        val movie = movieApi.detailMovie(movieId)
        movie
    }
}