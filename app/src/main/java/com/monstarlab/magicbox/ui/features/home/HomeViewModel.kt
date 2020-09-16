package com.monstarlab.magicbox.ui.features.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.monstarlab.magicbox.data.model.HomeResponse
import com.monstarlab.magicbox.data.repository.MagicBoxRepository
import com.monstarlab.magicbox.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel @ViewModelInject constructor(private val repository: MagicBoxRepository) : ViewModel() {

    private var _popularMoviesSuccessCallback = MutableLiveData<Resource<HomeResponse>>()
    val popularMovies: LiveData<Resource<HomeResponse>> = _popularMoviesSuccessCallback

    /**
     * Fetch PopularData
     */
    fun fetchPopularMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            _popularMoviesSuccessCallback.postValue(repository.getPopular().value)

        }
    }
}