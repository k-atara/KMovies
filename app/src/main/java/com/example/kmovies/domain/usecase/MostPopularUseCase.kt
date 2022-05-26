package com.example.kmovies.domain.usecase

import com.example.kmovies.domain.repository.MovieRepository
import javax.inject.Inject

class MostPopularUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    fun invoke() = repository.getMostPopularMovies()
}