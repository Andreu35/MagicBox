package com.monstarlab.magicbox.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.monstarlab.magicbox.data.local.dao.FavoriteDao
import com.monstarlab.magicbox.data.model.Movie

@Database(entities = [Movie::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favoriteDao(): FavoriteDao

}