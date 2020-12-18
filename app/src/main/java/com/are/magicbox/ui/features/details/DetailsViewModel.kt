package com.are.magicbox.ui.features.details

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.are.magicbox.data.model.HomeResponse
import com.are.magicbox.data.model.Movie
import com.are.magicbox.data.repository.MagicBoxRepository
import com.are.magicbox.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsViewModel @ViewModelInject constructor(private val repository: MagicBoxRepository) : ViewModel() {

    private var _movieSuccessCallback = MutableLiveData<Resource<Movie>>()
    val movie: LiveData<Resource<Movie>> = _movieSuccessCallback

    private var _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    /**
     * Fetch Movie by id.
     * @param id Movie id
     */
    fun fetchMovie(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _movieSuccessCallback.postValue(repository.getMovie(id).value)
        }
    }

    /**
     * Insert new favorite
     * @param movie Movie model
     */
    fun insertFavorite(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertFavorite(movie)
            _isFavorite.postValue(true)
        }
    }

    /**
     * Delete favorite
     * @param movie Movie model
     */
    fun deleteFavorite(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFavorite(movie)
            _isFavorite.postValue(false)
        }
    }

    /**
     * Check if the movie is saved or not
     * @param movie Moview model
     */
    fun checkIfExists(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            _isFavorite.postValue(repository.checkIfExists(movie.id))
        }
    }
}