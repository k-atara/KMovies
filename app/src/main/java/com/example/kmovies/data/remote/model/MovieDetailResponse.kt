package com.example.kmovies.data.remote.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieDetailResponse (
    val id: Int,
    val original_title: String,
    val genres: List<Genres>,
    val overview : String?,
    val poster_path: String,
    val vote_average: Double,
    val release_date: String,
    val homepage: String
): Parcelable {
    val baseUrl get() = "https://image.tmdb.org/t/p/w500"
}

@Parcelize
data class Genres(
    val id: Int,
    val name: String
):Parcelable