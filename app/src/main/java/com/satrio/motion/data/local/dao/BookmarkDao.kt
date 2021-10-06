package com.satrio.motion.data.local.dao

import androidx.room.*
import com.satrio.motion.data.entity.Movie
import com.satrio.motion.data.entity.MovieDB
import com.satrio.motion.util.AppConstant.Companion.TABLE_NAME
import java.util.*

/**
 * Data Access Object[DAO]
 */
@Dao
interface BookmarkDao {

    /**
     * Insert [Movie] into the Movie Table
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(Movie: MovieDB)

    /**
     * Delete movie from Database
     */
    @Delete
    suspend fun removeMovie(movie: MovieDB)

    /**
     * Delete [Movie] by [Movie.id]
     */
    @Query("Delete from ${TABLE_NAME} where id=:id")
    suspend fun deleteMovieById(id: Int)

    /**
     * Fetch all movies
     */
    @Query("Select * from $TABLE_NAME")
    fun getAllBookmark(): List<MovieDB>

    /**
     * Check movie exist in DB
     */
    @Query("SELECT EXISTS (SELECT 1 FROM $TABLE_NAME WHERE id = :id)")
    fun bookmarkExist(id: Int): Boolean


}