package com.monstarlab.magicbox.data.repository

import com.monstarlab.magicbox.data.local.dao.FavoriteDao
import com.monstarlab.magicbox.data.model.Movie
import com.monstarlab.magicbox.data.remote.MagicBoxDataSource
import javax.inject.Inject

class MagicBoxRepository @Inject constructor(private val remoteDataSource: MagicBoxDataSource, private val localDataSource: FavoriteDao) {

    suspend fun getMovie(id: Int) = remoteDataSource.getMovie(id)
    suspend fun searchMoviesWithPage(query: String, page: Int) = remoteDataSource.searchMoviesWithPage(query, page)
    suspend fun searchMovies(query: String) = remoteDataSource.searchMovies(query)
    fun insertFavorite(movie: Movie) = localDataSource.insertFavorite(movie)
    fun deleteFavorite(movie: Movie) = localDataSource.deleteFavorite(movie)
    fun checkIfExists(id: Int) = localDataSource.checkIfExists(id)
}