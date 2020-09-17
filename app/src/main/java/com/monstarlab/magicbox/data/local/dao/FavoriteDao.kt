package com.monstarlab.magicbox.data.local.dao

import androidx.room.*
import com.monstarlab.magicbox.data.model.Movie

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM Movie")
    fun getAll(): List<Movie>

    @Query("SELECT EXISTS(SELECT * FROM Movie WHERE id = :movieID)")
    fun checkIfExists(movieID: Int): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(vararg movies: Movie)

    @Update
    fun updateFavorite(movie: Movie)

    @Delete
    fun deleteFavorite(movie: Movie)

}