package com.monstarlab.magicbox.data.model

import androidx.room.Entity

@Entity
data class Movie (

    val popularity: Int,
    val vote_count: Int,
    val video: Boolean,
    val poster_path: String,
    val id: Int,
    val adult: Boolean,
    val backdrop_path: String,
    val original_language: String,
    val original_title: String,
    val genre_ids: IntArray,
    val title: String,
    val vote_average: Double,
    val overview: String,
    val release_date: String

) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Movie

        if (!genre_ids.contentEquals(other.genre_ids)) return false

        return true
    }

    override fun hashCode(): Int {
        return genre_ids.contentHashCode()
    }

    override fun toString(): String {
        return "Movie(" +
                "popularity=$popularity, " +
                "vote_count=$vote_count, " +
                "video=$video, " +
                "poster_path='$poster_path', " +
                "id=$id, adult=$adult, " +
                "backdrop_path='$backdrop_path', " +
                "original_language='$original_language', " +
                "original_title='$original_title', " +
                "genre_ids=${genre_ids.contentToString()}, " +
                "title='$title', " +
                "vote_average=$vote_average, " +
                "overview='$overview', " +
                "release_date='$release_date'" +
                ")"
    }


}