package com.monstarlab.magicbox.data.repository

import com.monstarlab.magicbox.data.local.dao.FavoriteDao
import com.monstarlab.magicbox.data.remote.MagicBoxDataSource
import javax.inject.Inject

class MagicBoxRepository @Inject constructor(private val remoteDataSource: MagicBoxDataSource, private val localDataSource: FavoriteDao) {

    suspend fun getPopular(page: Int) = remoteDataSource.getPopular(page)
    suspend fun getMovie(id: Int) = remoteDataSource.getMovie(id)
    suspend fun getSearched(query: String, page: Int) = remoteDataSource.searchMovie(query, page)
}