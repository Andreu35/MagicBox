package com.monstarlab.magicbox.data.remote

import android.content.Context
import com.monstarlab.magicbox.R
import com.monstarlab.magicbox.data.remote.net.MagicBoxAPI
import javax.inject.Inject

class MagicBoxDataSource @Inject constructor(private val api: MagicBoxAPI, private val context: Context): BaseDataSource() {

    suspend fun getPopular(page: Int) = getResult {
        api.getPopularMovies(context.getString(R.string.api_key), page)
    }

    suspend fun getMovie(id: Int) = getResult {
        api.getMovie(id, context.getString(R.string.api_key))
    }

    suspend fun searchMovie(query: String, page: Int) = getResult {
        api.getSearchMovies(context.getString(R.string.api_key), query, page)
    }
}