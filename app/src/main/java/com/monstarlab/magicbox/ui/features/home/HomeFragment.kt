package com.monstarlab.magicbox.ui.features.home

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.monstarlab.magicbox.R
import com.monstarlab.magicbox.data.model.Movie
import com.monstarlab.magicbox.databinding.FragmentHomeBinding
import com.monstarlab.magicbox.extensions.autoCleared
import com.monstarlab.magicbox.extensions.goneUnless
import com.monstarlab.magicbox.ui.BaseFragment
import com.monstarlab.magicbox.ui.common.RecyclerItemClickListener
import com.monstarlab.magicbox.utils.Constants
import com.monstarlab.magicbox.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment(), RecyclerItemClickListener {

    private var binding: FragmentHomeBinding by autoCleared()
    private val viewModel: HomeViewModel by viewModels()
    private var adapter: HomeAdapter? = null
    private var page: Int = 0
    private var totalPages: Int = 0
    private var querySearch: String = ""

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if(!recyclerView.canScrollVertically(0) && page < totalPages){
                binding.recyclerView.removeOnScrollListener(this)
                fetchSearchedMoviesWithPage(querySearch, page + 1)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_home, menu)

        val actionMenuItem = menu.findItem(R.id.nav_search)
        val searchView = actionMenuItem.actionView as SearchView
        searchView.isIconified = true

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                adapter?.cleanAdapter()
                adapter = null
                fetchSearchedMovies(query)
                actionMenuItem.collapseActionView()
                return false
            }
            override fun onQueryTextChange(s: String?): Boolean { return false }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        setUpToolbar(binding.toolbar, showTitle = true, showHome = false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.movieList.observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.progressBar.goneUnless(false)
                    if (it.data != null && !it.data.results.isNullOrEmpty()) {
                        // Get the current and total page
                        page = it.data.page
                        totalPages = it.data.total_pages

                        pushAdapter(it.data.results)

                        binding.recyclerView.addOnScrollListener(scrollListener)
                    }
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    binding.progressBar.goneUnless(false)
                }
                else -> {
                }
            }
        })
    }

    /**
     * Get the searched movies from API
     */
    private fun fetchSearchedMovies(query: String) {
        querySearch = query
        binding.progressBar.goneUnless(true)
        binding.recyclerView.goneUnless(true)
        binding.infoLayout.goneUnless(false)
        viewModel.searchMovies(query)
    }

    /**
     * Get the searched movies from API by next page
     */
    private fun fetchSearchedMoviesWithPage(query: String, page: Int) {
        binding.progressBar.goneUnless(true)
        binding.recyclerView.goneUnless(true)
        binding.infoLayout.goneUnless(false)
        viewModel.searchMoviesWithPage(query, page)
    }

    /**
     * Initialize the Adapter if null and bind to RecyclerView.
     * If not null, insert new items to the list
     * @param movies Movies List
     */
    private fun pushAdapter(movies: MutableList<Movie>) {
        if(adapter == null) {
            adapter = HomeAdapter(requireContext(), movies, this@HomeFragment, getString(R.string.searched))
            binding.recyclerView.adapter = adapter
        }else{
            adapter?.insertMoreMovies(movies)
        }
    }

    /**
     * RecyclerView Item Click Listener
     * @param view View Clicked
     * @param movie Movie Model
     * @param position Item clicked position
     */
    override fun onItemClick(view: View, movie: Movie, position: Int) {
        val bundle = Bundle()
        bundle.putString(Constants.TITLE, movie.title)
        bundle.putInt(Constants.ID, movie.id)
        bundle.putString(Constants.TRANSITION_NAME, view.transitionName)
        openNewFragmentWithTransition(bundle, view, R.id.action_home_to_details)
    }

    /**
     * Select the MenuItem by ID
     * @param item MenuItem
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_favorite -> {
                openNewFragment(R.id.action_home_to_favorite)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}