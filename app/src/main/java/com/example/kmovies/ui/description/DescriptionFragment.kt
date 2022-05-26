package com.example.kmovies.ui.description

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.kmovies.databinding.FragmentDescriptionBinding

class DescriptionFragment : Fragment() {

    private lateinit var binding: FragmentDescriptionBinding
    private val args by navArgs<DescriptionFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDescriptionBinding.inflate(inflater, container, false)

        val movie = args.movie
        binding.apply {
            Glide.with(this@DescriptionFragment)
                .load("${movie.baseUrl}${movie.poster_path}")
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imgImage)
            txtName.text = movie.original_title
            txtAverage.text = movie.vote_average.toString()
            txtDescription.text = movie.overview
        }
        return binding.root
    }
}