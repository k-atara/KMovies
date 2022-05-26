package com.example.kmovies.data.remote.service

import com.example.kmovies.data.remote.model.MovieDetailResponse
import com.example.kmovies.data.remote.model.MoviesResponse
import com.example.kmovies.util.CommonConstants.API_KEY
import com.example.kmovies.util.CommonConstants.LANGUAGE_US
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("movie/now_playing?api_key=$API_KEY&language=$LANGUAGE_US")
    suspend fun getNowPlayingMovies(
        @Query("page") position: Int
    ): MoviesResponse

    @GET("movie/popular?api_key=$API_KEY&language=$LANGUAGE_US")
    suspend fun getPopularMovies(
        @Query("page") position: Int
    ): MoviesResponse

    @GET("search/movie?api_key=$API_KEY")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("page") page: Int
    ): MoviesResponse

    @GET("movie/{movie_id}?api_key=$API_KEY&language=$LANGUAGE_US")
    suspend fun detailMovie(
        @Path("movie_id") movieId: Int
    ): Response<MovieDetailResponse>

}