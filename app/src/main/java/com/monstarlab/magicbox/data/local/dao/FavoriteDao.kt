package com.monstarlab.magicbox.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.monstarlab.magicbox.data.model.Movie

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM Movie")
    fun getAll(): List<Movie>

    @Insert
    fun insertAll(vararg movies: Movie)

    @Delete
    fun delete(movie: Movie)

}