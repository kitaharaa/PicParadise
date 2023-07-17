package com.playrix.gar.data.room.entity

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.playrix.gar.data.room.entity.LikedPhotos.LikedCompanion.tableName

@Entity(tableName = tableName)
data class LikedPhotos(
    @PrimaryKey(autoGenerate = true) var photoId: Int =0,
    @ColumnInfo(name = photoField) val photoBitmap: Bitmap,
    @ColumnInfo(name = timeField) val time: String
) {
    companion object LikedCompanion {
        const val tableName = "liked_table"

        const val photoField = "liked_photo"
        const val timeField = "liked_time"
    }
}