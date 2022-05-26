package com.example.kmovies.data.remote.service

import com.example.kmovies.data.remote.model.MoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    companion object{
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val API_KEY = "9f0e8e4a83a1940ca18a278e6fdbebe5"
        const val CONTENT_TYPE_H = "application/json;charset=utf-8"
        const val AUTHORIZATION = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI5ZjBlOGU0YTgzYTE5NDBjYTE4YTI3OGU2ZmRiZWJlNSIsInN1YiI6IjYyOGUyNjc5MWEzMjQ4M2I2MjQ1M2NhYiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.EoRfjHM3tYoFC7mnRMVu1g__6SJFYLIbKHX7C7xDnn8"
    }

    @GET("movie/now_playing?api_key=$API_KEY")
    suspend fun getNowPlayingMovies(
        @Query("page") position: Int
    ): MoviesResponse

    @GET("movie/now_playing?api_key=$API_KEY")
    suspend fun getPopularMovies(
        @Query("page") position: Int
    ): MoviesResponse

    @GET("search/movie?api_key=$API_KEY")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("page") page: Int
    ): MoviesResponse

}