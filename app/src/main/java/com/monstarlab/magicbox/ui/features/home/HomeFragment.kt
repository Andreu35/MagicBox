package com.monstarlab.magicbox.ui.features.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.monstarlab.magicbox.data.model.Movie
import com.monstarlab.magicbox.databinding.FragmentHomeBinding
import com.monstarlab.magicbox.extensions.autoCleared
import com.monstarlab.magicbox.extensions.goneUnless
import com.monstarlab.magicbox.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var binding: FragmentHomeBinding by autoCleared()
    private val viewModel: HomeViewModel by viewModels()
    private var adapter: HomeAdapter? = null
    private var page: Int = 1
    private var totalPages: Int = 0

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if(!recyclerView.canScrollVertically(0) && page < totalPages){
                binding.recyclerView.removeOnScrollListener(this)
                fetchPopularMovies(page+1)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.movieList.observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.progressBar.goneUnless(false)
                    if (it.data != null) {
                        // Get the current and total page
                        page = it.data.page
                        totalPages = it.data.total_pages

                        pushAdapter(it.data.results)

                    }
                    binding.recyclerView.addOnScrollListener(scrollListener)
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    binding.progressBar.goneUnless(false)
                }
                else -> {}
            }
        })

        fetchPopularMovies(page)
    }

    private fun fetchPopularMovies(page: Int) {
        binding.progressBar.goneUnless(true)
        viewModel.fetchPopularMovies(page)
    }

    private fun pushAdapter(movies: MutableList<Movie>) {
        // Initialize the Adapter if null and bind to RecyclerView
        // If not null, insert new items to the list
        if(adapter == null) {
            adapter = HomeAdapter(requireContext(), movies)
            binding.recyclerView.adapter = adapter
        }else{
            adapter?.insertMoreMovies(movies)
        }
    }
}