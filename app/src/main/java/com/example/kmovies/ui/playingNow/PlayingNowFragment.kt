package com.example.kmovies.ui.playingNow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.kmovies.data.remote.model.MovieResponse
import com.example.kmovies.databinding.FragmentPlayingnowBinding
import com.example.kmovies.ui.description.DescriptionFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PlayingNowFragment : Fragment(), PlayingNowAdapter.OnItemClickListener {

    private lateinit var binding: FragmentPlayingnowBinding
    private val viewModel by viewModels<PlayingNowViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlayingnowBinding.inflate(inflater, container, false)

        val adapter = PlayingNowAdapter(this)

        binding.apply {
            playinNowList.setHasFixedSize(true)
            playinNowList.adapter = adapter.withLoadStateHeaderAndFooter(
                header = PlayingNowLoadStateAdapter {adapter.retry()},
                footer = PlayingNowLoadStateAdapter {adapter.retry()}
            )
            playinNowList.layoutManager= GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            btnTryAgain.setOnClickListener {
                adapter.retry()
            }
        }

        viewModel.collectMovies().observe(viewLifecycleOwner){
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        adapter.addLoadStateListener { loadState ->
            binding.apply {
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                playinNowList.isVisible = loadState.source.refresh is LoadState.NotLoading
                btnTryAgain.isVisible =loadState.source.refresh is LoadState.Error

                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    adapter.itemCount < 1){
                    playinNowList.isVisible = false
                }
            }
        }
        return binding.root
    }

    override fun onItemClick(movie: MovieResponse) {
        findNavController().navigate(PlayingNowFragmentDirections.toDescriptionFragment(movie))
    }

}