package com.monstarlab.magicbox.ui.features.favorite

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.monstarlab.magicbox.data.model.HomeResponse
import com.monstarlab.magicbox.data.model.Movie
import com.monstarlab.magicbox.data.repository.MagicBoxRepository
import com.monstarlab.magicbox.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel @ViewModelInject constructor(private val repository: MagicBoxRepository) : ViewModel() {

    private var _favoriteSuccessCallback = MutableLiveData<List<Movie>>()
    val favorites: LiveData<List<Movie>> = _favoriteSuccessCallback

    private var _isFavorite = MutableLiveData<Long>()
    val isFavorite: LiveData<Long> = _isFavorite

    /**
     * Fetch all Favorites from DB.
     */
    fun fetchAllFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            _favoriteSuccessCallback.postValue(repository.getAllFavorites())
        }
    }

    /**
     * Delete favorite
     * @param movie Movie model
     */
    fun deleteFavorite(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFavorite(movie)
            _isFavorite.postValue(System.currentTimeMillis())
        }
    }
}