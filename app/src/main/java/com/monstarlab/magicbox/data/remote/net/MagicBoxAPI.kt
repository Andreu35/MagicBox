package com.monstarlab.magicbox.data.remote.net

import com.monstarlab.magicbox.data.model.Movie
import com.monstarlab.magicbox.data.model.Respponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface MagicBoxAPI {

    @GET("/3/movie/popular")
    fun getPopularMovies(@Query("api_key") key: String): Response<Respponse>

    @GET("/3/search/movie")
    fun getSearchMovies(
        @Query("api_key") key: String,
        @Query("query") query: String): Response<Respponse>

    @GET("/3/movie")
    fun getMovie(
        @Query("movie_id") id: Int,
        @Query("api_key") key: String): Response<Movie>
}