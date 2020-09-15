package com.monstarlab.magicbox.data.model

import androidx.room.*
import java.util.*
import kotlin.collections.ArrayList

@Entity(tableName = "movie")
data class Movie (

    @PrimaryKey
    val id: Int,
    val popularity: Int,
    val vote_count: Int,
    val video: Boolean,
    val poster_path: String,
    val adult: Boolean,
    val backdrop_path: String,
    val original_language: String,
    val original_title: String,
    @Embedded
    val genre_ids: ArrayList<Genre>,
    val title: String,
    val vote_average: Double,
    val overview: String,
    val release_date: String

) {

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
                "genre_ids=$genre_ids, " +
                "title='$title', " +
                "vote_average=$vote_average, " +
                "overview='$overview', " +
                "release_date='$release_date'" +
                ")"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Movie

        return id == other.id
            && popularity == other.popularity
            && vote_count == other.vote_count
            && video == other.video
            && poster_path == other.poster_path
            && adult == other.adult
            && backdrop_path == other.backdrop_path
            && original_language == other.original_language
            && original_title == other.original_title
            && genre_ids == other.genre_ids
            && title == other.title
            && vote_average == other.vote_average
            && overview == other.overview
            && release_date == other.release_date
    }

    override fun hashCode(): Int {
        return Objects.hash(
            id,
            popularity,
            vote_count,
            video,
            poster_path,
            adult,
            backdrop_path,
            original_language,
            original_title,
            genre_ids,
            title,
            vote_average,
            overview,
            release_date
        )
    }

}