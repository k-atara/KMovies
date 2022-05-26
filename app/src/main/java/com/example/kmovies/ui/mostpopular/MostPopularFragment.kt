package com.example.kmovies.ui.mostpopular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kmovies.data.remote.model.MovieResponse
import com.example.kmovies.databinding.FragmentMostpopularBinding
import com.example.kmovies.ui.playingNow.*
import com.example.kmovies.util.MovieUIState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MostPopularFragment : Fragment(), PlayingNowAdapter.OnItemClickListener {

    private lateinit var binding: FragmentMostpopularBinding
    private val viewModel by viewModels<MostPopularViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMostpopularBinding.inflate(inflater, container, false)

        val adapter = PlayingNowAdapter(this)

        binding.apply {
            popularList.setHasFixedSize(true)
            popularList.adapter = adapter.withLoadStateHeaderAndFooter(
                header = PlayingNowLoadStateAdapter {adapter.retry()},
                footer = PlayingNowLoadStateAdapter {adapter.retry()}
            )
            popularList.layoutManager= GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            btnTryAgain.setOnClickListener {
                adapter.retry()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            setObservable(adapter)
        }

        adapter.addLoadStateListener { loadState ->
            binding.apply {
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                popularList.isVisible = loadState.source.refresh is LoadState.NotLoading
                txtError.isVisible =loadState.source.refresh is LoadState.Error
                btnTryAgain.isVisible =loadState.source.refresh is LoadState.Error

                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    adapter.itemCount < 1){
                    popularList.isVisible = false
                }
            }
        }
        observeUI()
        return binding.root
    }

    override fun onItemClick(movie: MovieResponse) {
        viewModel.getMovie(movie.id)
    }

    private fun setObservable(adapter: PlayingNowAdapter) = viewLifecycleOwner.lifecycleScope.launch{
        viewModel.collectMovies().observeForever {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    private fun observeUI() {
        viewModel.movieState.observe(viewLifecycleOwner) {
            when (it) {
                is MovieUIState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    findNavController().navigate(MostPopularFragmentDirections.toDescriptionFragment(it.movie))
                }
                is MovieUIState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    it.error.let { message ->
                        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
                    }
                }
                is MovieUIState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }

        }
    }
}