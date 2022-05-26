package com.example.kmovies.data.remote

import androidx.paging.PagingSource
import com.example.kmovies.data.remote.model.MovieResponse
import com.example.kmovies.data.remote.service.MovieService
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

class MoviePagingSource (
    private val movieApi: MovieService,
    private val query: String?,
    private val popular: Boolean? = false
): PagingSource<Int, MovieResponse>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieResponse> {
        return try {
            val position = params.key ?: STARTING_PAGE_INDEX
            val response = if (query != null) movieApi.searchMovies(query,position)
                            else if(popular == true) movieApi.getPopularMovies(position)
                            else movieApi.getNowPlayingMovies(position)
            val movies = response.results

            LoadResult.Page(
                data = movies,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position-1,
                nextKey = if (movies.isEmpty()) null else position+1
            )
        } catch (e: IOException){
            LoadResult.Error(e)
        } catch (e: HttpException){
            LoadResult.Error(e)
        }

    }
}