package com.are.magicbox.data.remote

import android.content.Context
import com.are.magicbox.R
import com.are.magicbox.data.remote.net.MagicBoxAPI
import javax.inject.Inject

class MagicBoxDataSource @Inject constructor(private val api: MagicBoxAPI, private val context: Context): BaseDataSource() {

    suspend fun getMovie(id: Int) = getResult {
        api.getMovie(id, context.getString(R.string.api_key))
    }

    suspend fun searchMoviesWithPage(query: String, page: Int) = getResult {
        api.getSearchMoviesWithPage(context.getString(R.string.api_key), query, page)
    }

    suspend fun searchMovies(query: String) = getResult {
        api.getSearchMovies(context.getString(R.string.api_key), query)
    }
}