package com.are.magicbox.data.model

import androidx.room.*
import java.io.Serializable
import kotlin.collections.ArrayList

@Entity
data class Movie(

    @PrimaryKey val id: Int,
    val imdb_id: String? = "",
    val adult: Boolean = false,
    val backdrop_path: String? = "",
    val budget: Int? = 0,
    val genres: List<Genre>? = ArrayList(),
    val homepage: String? = "",
    val original_language: String? = "",
    val original_title: String? = "",
    val overview: String? = "",
    val popularity: Float? = 0.0f,
    val poster_path: String? = "",
    val release_date: String? = "",
    val revenue: Int? = 0,
    val runtime: Int? = 0,
    val status: String? = "",
    val tagline: String? = "",
    val title: String? = "",
    val video: Boolean? = false,
    val vote_average: Float? = 0.0f,
    val vote_count: Int? = 0

): Serializable