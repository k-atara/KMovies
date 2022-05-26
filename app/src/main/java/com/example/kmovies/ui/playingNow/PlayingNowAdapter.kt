package com.example.kmovies.ui.playingNow

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.kmovies.data.remote.model.MovieResponse
import com.example.kmovies.databinding.MovieItemBinding

class PlayingNowAdapter(private val listener : OnItemClickListener) : PagingDataAdapter<MovieResponse, PlayingNowAdapter.MovieViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    inner class MovieViewHolder(private val binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION){
                    val item = getItem(position)
                    if (item!=null){
                        listener.onItemClick(item)
                    }
                }
            }
        }

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

    interface OnItemClickListener{
        fun onItemClick(movie: MovieResponse)
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<MovieResponse>() {
            override fun areItemsTheSame(oldItem: MovieResponse, newItem: MovieResponse): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: MovieResponse, newItem: MovieResponse): Boolean =
                oldItem == newItem
        }
    }

}