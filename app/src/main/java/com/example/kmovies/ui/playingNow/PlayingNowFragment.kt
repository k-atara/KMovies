package com.example.kmovies.ui.playingNow

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kmovies.R
import com.example.kmovies.data.remote.model.MovieResponse
import com.example.kmovies.databinding.FragmentPlayingnowBinding
import com.example.kmovies.util.MovieUIState
import dagger.hilt.android.AndroidEntryPoint

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
                txtError.isVisible =loadState.source.refresh is LoadState.Error
                btnTryAgain.isVisible =loadState.source.refresh is LoadState.Error

                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    adapter.itemCount < 1){
                        playinNowList.isVisible = false
                    }
            }
        }
        observeUI()
        setHasOptionsMenu(true)

        if(binding.playinNowList.verticalScrollbarPosition > 6)
            binding.image.visibility = View.GONE
        else
            binding.image.visibility = View.VISIBLE
        return binding.root
    }

    override fun onItemClick(movie: MovieResponse) {
        viewModel.getMovie(movie.id)
    }

    private fun observeUI() {
        viewModel.movieState.observe(viewLifecycleOwner) {
            when (it) {
                is MovieUIState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    findNavController().navigate(PlayingNowFragmentDirections.toDescriptionFragment("playing", it.movie))
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_search, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query!=null){
                    binding.playinNowList.scrollToPosition(0)
                    viewModel.searchMovies(query)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
    }
}