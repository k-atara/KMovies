package com.example.kmovies.data.remote.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieResponse (
    val id: String,
    val original_title: String,
    val overview : String?,
    val poster_path: String,
    val vote_average: Double
): Parcelable {
    val baseUrl get() = "https://image.tmdb.org/t/p/w500"
}