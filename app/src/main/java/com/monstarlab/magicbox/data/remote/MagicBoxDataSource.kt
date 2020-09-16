package com.monstarlab.magicbox.data.remote

import android.content.Context
import com.monstarlab.magicbox.R
import com.monstarlab.magicbox.data.remote.net.MagicBoxAPI
import javax.inject.Inject

class MagicBoxDataSource @Inject constructor(private val api: MagicBoxAPI, private val context: Context): BaseDataSource() {

    suspend fun getPopular() = getResult {
        api.getPopularMovies(context.getString(R.string.api_key), context.getString(R.string.api_token))
    }

    suspend fun getMovie(id: Int) = getResult {
        api.getMovie(id, context.getString(R.string.api_key), context.getString(R.string.api_token))
    }

    suspend fun searchMovie(query: String) = getResult {
        api.getSearchMovies(context.getString(R.string.api_key), query, context.getString(R.string.api_token))
    }
}