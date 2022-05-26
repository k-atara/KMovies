package com.example.kmovies.holder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.kmovies.data.remote.model.MovieResponse
import com.example.kmovies.databinding.MovieItemBinding

class PlayingNowHolder(val binding: MovieItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: MovieResponse) {
        with(binding) {
            Glide.with(itemView)
                .load("${movie.baseUrl}${movie.poster_path}")
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(image)
            nameTxt.text = movie.original_title
            averageTxt.text = movie.vote_average.toString()
        }
    }
}