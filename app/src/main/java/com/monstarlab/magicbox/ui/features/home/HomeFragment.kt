package com.monstarlab.magicbox.ui.features.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.monstarlab.magicbox.data.model.Movie
import com.monstarlab.magicbox.databinding.FragmentHomeBinding
import com.monstarlab.magicbox.extensions.autoCleared
import com.monstarlab.magicbox.extensions.goneUnless
import com.monstarlab.magicbox.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var binding: FragmentHomeBinding by autoCleared()
    private val viewModel: HomeViewModel by viewModels()
    private var adapter: HomeAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.popularMovies.observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.progressBar.goneUnless(false)
                    if (it.data != null) {
                        if(adapter == null) {
                            val items = ArrayList<Movie>()
                            items.addAll(it.data.results)
                            adapter = HomeAdapter(requireContext(), items)
                            binding.recyclerView.adapter = adapter
                        }

                    }
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    binding.progressBar.goneUnless(false)
                }
                Resource.Status.LOADING -> {
                    binding.progressBar.goneUnless(true)
                }
            }
        })

        viewModel.fetchPopularMovies()
    }

}