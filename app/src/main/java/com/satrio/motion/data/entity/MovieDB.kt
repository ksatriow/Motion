package com.satrio.motion.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.satrio.motion.util.AppConstant

@Entity(tableName = AppConstant.TABLE_NAME)
data class MovieDB(
    @PrimaryKey
    val id: Int,
    val poster_path: String,
    val overview: String,
    val title: String,
    val backdrop_path: String
)