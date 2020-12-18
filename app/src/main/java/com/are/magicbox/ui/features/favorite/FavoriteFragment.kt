package com.are.magicbox.ui.features.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.are.magicbox.R
import com.are.magicbox.data.model.Movie
import com.are.magicbox.databinding.FavoriteFragmentBinding
import com.are.magicbox.extensions.autoCleared
import com.are.magicbox.extensions.goneUnless
import com.are.magicbox.ui.BaseFragment
import com.are.magicbox.ui.common.RecyclerItemClickListener
import com.are.magicbox.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : BaseFragment(), RecyclerItemClickListener {
    private var binding: FavoriteFragmentBinding by autoCleared()
    private val viewModel: FavoriteViewModel by viewModels()
    private var adapter: FavoriteAdapter? = null
    private var position = 0
    private var movie: Movie? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FavoriteFragmentBinding.inflate(layoutInflater, container, false)
        setUpToolbar(binding.toolbar, showTitle = true, showHome = true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.favorites.observe(viewLifecycleOwner, {
            if(it.isNotEmpty()) {
                binding.progressBar.goneUnless(false)
                binding.infoLayoutFavorite.goneUnless(false)
                binding.recyclerView.goneUnless(true)
                adapter = FavoriteAdapter(requireActivity(), it as MutableList<Movie>, this)
                binding.recyclerView.adapter = adapter
            }else{
                binding.progressBar.goneUnless(false)
                binding.infoLayoutFavorite.goneUnless(true)
                binding.recyclerView.goneUnless(false)
            }
        })

        viewModel.isFavorite.observe(viewLifecycleOwner, {
            adapter?.deleteItem(position, movie!!)
        })

        viewModel.fetchAllFavorites()
    }

    override fun onItemClick(view: View, movie: Movie, position: Int) {
        when (view.tag) {
            COVER -> {
                val bundle = Bundle()
                bundle.putString(Constants.TITLE, movie.title)
                bundle.putInt(Constants.ID, movie.id)
                bundle.putString(Constants.TRANSITION_NAME, view.transitionName)
                openNewFragmentWithTransition(bundle, view, R.id.action_favorite_to_details)
            }
            else -> {
                this.position = position
                this.movie = movie
                viewModel.deleteFavorite(movie)
            }
        }
    }

    /**
     * Select the MenuItem by ID
     * @param item MenuItem
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val COVER = "Cover"
    }
}