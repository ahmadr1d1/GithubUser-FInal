package com.ahmadrd.githubuser.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoriteUser::class], version = 1, exportSchema = false)
abstract class FavUserDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        @Volatile
        private var instance: FavUserDatabase? = null
        fun getInstance(context: Context): FavUserDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    FavUserDatabase::class.java, "Favorite.db"
                ).build()
            }
    }
}