package com.monstarlab.magicbox.data.remote.net

import com.monstarlab.magicbox.data.model.Movie
import com.monstarlab.magicbox.data.model.HomeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


interface MagicBoxAPI {

    @GET("/3/movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") key: String,
        @Query("page") page: Int): Response<HomeResponse>

    @GET("/3/search/movie")
    suspend fun getSearchMovies(
        @Query("api_key") key: String,
        @Query("query") query: String,
        @Query("page") page: Int): Response<HomeResponse>

    @GET("/3/movie")
    suspend fun getMovie(
        @Query("movie_id") id: Int,
        @Query("api_key") key: String): Response<Movie>
}