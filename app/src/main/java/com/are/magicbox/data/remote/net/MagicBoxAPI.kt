package com.are.magicbox.data.remote.net

import com.are.magicbox.data.model.Movie
import com.are.magicbox.data.model.HomeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query


interface MagicBoxAPI {

    @GET("/3/search/movie")
    suspend fun getSearchMoviesWithPage(
        @Query("api_key") key: String,
        @Query("query") query: String,
        @Query("page") page: Int): Response<HomeResponse>

    @GET("/3/search/movie")
    suspend fun getSearchMovies(
        @Query("api_key") key: String,
        @Query("query") query: String): Response<HomeResponse>

    @GET("/3/movie/{movie_id}")
    suspend fun getMovie(
        @Path("movie_id") id: Int,
        @Query("api_key") key: String): Response<Movie>
}