package com.are.magicbox.ui.features.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.are.magicbox.R
import com.are.magicbox.data.model.Genre
import com.are.magicbox.data.model.Movie
import com.are.magicbox.databinding.FragmentDetailsBinding
import com.are.magicbox.extensions.autoCleared
import com.are.magicbox.extensions.goneUnless
import com.are.magicbox.ui.BaseFragment
import com.are.magicbox.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class DetailsFragment : BaseFragment(), View.OnClickListener {

    private var binding: FragmentDetailsBinding by autoCleared()
    private val viewModel: DetailsViewModel by viewModels()
    private var movie: Movie? = null
    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        Timber.d(args.toString())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(layoutInflater, container, false)
        setUpToolbar(binding.detailsToolbar, showTitle = false, showHome = true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.coverDetails.transitionName = args.transitionName

        binding.favoriteIcon.setOnClickListener(this)

        viewModel.movie.observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    movie = it.data
                    Glide.with(this).load(getString(R.string.images_base_url, it.data?.backdrop_path)).into(binding.backgroundDetails)
                    Glide.with(this).load(getString(R.string.images_base_url, it.data?.poster_path)).into(binding.coverDetails)
                    binding.detailsData.detailsTitle.text = it.data?.title
                    binding.detailsData.detailsReleaseDate.text = getString(R.string.release_date, it.data?.release_date)
                    binding.detailsData.ratingBar.setStar(it.data?.vote_average!!/2)
                    binding.detailsData.overviewText.text = it.data.overview
                    setGenreChips(it.data.genres!!)
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
            binding.favoriteIcon.setImageResource(if (it) R.drawable.ic_favorite_on else R.drawable.ic_favorite_off)
        })

        viewModel.fetchMovie(args.movieId)
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
                if (viewModel.isFavorite.value!!) {
                    viewModel.deleteFavorite(movie!!)
                } else {
                    viewModel.insertFavorite(movie!!)
                }
            }
        }
    }

    /**
     * Generate the Genre chips to show on Details data
     * @param categories Genre List
     */
    @SuppressLint("InflateParams")
    fun setGenreChips(categories: List<Genre?>) {
        for (category in categories) {
            val mChip = this.layoutInflater.inflate(R.layout.item_chip_genre, null, false) as Chip
            mChip.text = category?.name
            val paddingDp = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10f, resources.displayMetrics).toInt()
            mChip.setPadding(paddingDp, 0, paddingDp, 0)
            mChip.setOnCheckedChangeListener { _, _ -> }
            binding.detailsData.genreView.addView(mChip)
        }
    }
}