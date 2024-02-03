package com.ahmadrd.githubuser.data.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "FavoriteUser")
@Parcelize
data class FavoriteUser(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    var username: String? = null,

    var avatarUrl: String? = null,
): Parcelable