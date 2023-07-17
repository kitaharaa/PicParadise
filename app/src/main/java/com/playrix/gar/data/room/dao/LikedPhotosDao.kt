package com.playrix.gar.data.room.dao

import androidx.room.*
import com.playrix.gar.data.room.entity.LikedPhotos
import com.playrix.gar.data.room.entity.LikedPhotos.LikedCompanion.tableName

@Dao
interface LikedPhotosDao {
    @Query("SELECT * FROM $tableName")
    fun getAll(): List<LikedPhotos>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertValue(likedPhotos: LikedPhotos)

    @Delete
    fun delete(user: LikedPhotos)
}