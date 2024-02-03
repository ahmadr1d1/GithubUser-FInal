package com.ahmadrd.githubuser.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Query("SELECT * from FavoriteUser WHERE username = :username")
    fun setFavUsers(username: String): Flow<List<FavoriteUser>>

    @Query("SELECT * from FavoriteUser ORDER BY username ASC")
    fun getFavUsers(): Flow<List<FavoriteUser>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: FavoriteUser)

    @Query("DELETE FROM FavoriteUser WHERE id = :id")
    suspend fun delete(id: Int)
}