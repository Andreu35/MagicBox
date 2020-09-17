package com.monstarlab.magicbox.ui.features.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.monstarlab.magicbox.R
import com.monstarlab.magicbox.data.model.Movie
import com.monstarlab.magicbox.databinding.FragmentDetailsBinding
import com.monstarlab.magicbox.extensions.autoCleared
import com.monstarlab.magicbox.extensions.goneUnless
import com.monstarlab.magicbox.ui.BaseFragment
import com.monstarlab.magicbox.utils.Constants
import com.monstarlab.magicbox.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class DetailsFragment : BaseFragment(), View.OnClickListener {

    private var binding: FragmentDetailsBinding by autoCleared()
    private val viewModel: DetailsViewModel by viewModels()

    private var movieID: Int = 0
    private var movieTitle: String = ""
    private var movie: Movie? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        movieID = requireArguments().getInt(Constants.ID)
        movieTitle = requireArguments().getString(Constants.TITLE).toString()
        transitionName = requireArguments().getString(Constants.TRANSITION_NAME).toString()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDetailsBinding.inflate(layoutInflater, container, false)
        setUpToolbar(binding.detailsToolbar, showTitle = false, showHome = true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.coverDetails.transitionName = transitionName

        binding.favoriteIcon.setOnClickListener(this)

        viewModel.movie.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    movie = it.data
                    Glide.with(this).load(getString(R.string.images_base_url, it.data?.backdrop_path)).into(binding.backgroundDetails)
                    Glide.with(this).load(getString(R.string.images_base_url, it.data?.poster_path)).into(binding.coverDetails)
                    binding.detailsTitle.text = it.data?.title
                    binding.detailsReleaseDate.text = getString(R.string.release_date, it.data?.release_date)
                    binding.ratingBar.setStar(it.data?.popularity!!)
                    binding.overviewText.text = it.data.overview
                    binding.progressBar.goneUnless(false)
                    viewModel.checkIfExists(it.data)

                }
                Resource.Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    binding.progressBar.goneUnless(false)
                }
                else -> {}
            }
        })

        viewModel.isFavorite.observe(viewLifecycleOwner, {
            binding.favoriteIcon.setImageResource(if(it) R.drawable.ic_favorite_on else R.drawable.ic_favorite_off)
        })

        viewModel.fetchMovie(movieID)
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

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.favoriteIcon -> {
                if(viewModel.isFavorite.value!!) {
                    viewModel.deleteFavorite(movie!!)
                }else{
                    viewModel.insertFavorite(movie!!)
                }
            }
        }
    }
}