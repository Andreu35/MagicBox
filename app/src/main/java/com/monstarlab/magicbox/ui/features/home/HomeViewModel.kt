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

    private var _moviesSuccessCallback = MutableLiveData<Resource<HomeResponse>>()
    val movieList: LiveData<Resource<HomeResponse>> = _moviesSuccessCallback

    /**
     * Search Movie from Query and Page
     * @param query Query Text
     * @param page Page Number
     */
    fun searchMoviesWithPage(query: String, page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _moviesSuccessCallback.postValue(repository.searchMoviesWithPage(query, page).value)
        }
    }

    /**
     * Search Movie from Query
     * @param query Query Text
     */
    fun searchMovies(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _moviesSuccessCallback.postValue(repository.searchMovies(query).value)
        }
    }
}