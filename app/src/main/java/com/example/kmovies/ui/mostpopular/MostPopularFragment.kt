package com.example.kmovies.ui.mostpopular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.kmovies.databinding.FragmentMostpopularBinding
import com.example.kmovies.ui.playingNow.PlayingNowViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MostPopularFragment : Fragment() {

    private lateinit var binding: FragmentMostpopularBinding
    private val viewModel by viewModels<MostPopularViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMostpopularBinding.inflate(inflater, container, false)
        return binding.root
    }

}