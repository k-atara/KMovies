package com.example.kmovies.ui.description

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.kmovies.R
import com.example.kmovies.data.remote.model.MovieDetailResponse
import com.example.kmovies.databinding.FragmentDescriptionBinding
import com.example.kmovies.util.DateFormatConstants.YYYY
import com.example.kmovies.util.DateFormatConstants.YYYY_MM_DD
import com.example.kmovies.util.formatDateToNewFormat

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
        setView(movie)
        setHasOptionsMenu(true)
        return binding.root
    }

    private fun setView(movie: MovieDetailResponse){
        binding.apply {
            Glide.with(this@DescriptionFragment)
                .load("${movie.baseUrl}${movie.poster_path}")
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imgImage)
            var dateFormat = formatDateToNewFormat(YYYY_MM_DD, YYYY, movie.release_date)
            txtName.text = "${movie.original_title} ($dateFormat)"
            txtAverage.text = movie.vote_average.toString()
            txtGenres.text = movie.genres.joinToString { it -> "${it.name}"}
            txtDescription.text = movie.overview
            txtLink.text = getString(R.string.link_page, movie.homepage)
            txtLink.movementMethod = LinkMovementMethod.getInstance();
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (args.from) {
            "playing" -> findNavController().navigate(R.id.toNavigationPlayingnow)
            "popular" -> findNavController().navigate(R.id.toMostpopular)
        }
        return true
    }
}