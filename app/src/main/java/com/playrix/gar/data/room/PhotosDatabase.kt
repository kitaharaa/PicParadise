package com.playrix.gar.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.playrix.gar.data.room.dao.LikedPhotosDao
import com.playrix.gar.data.room.entity.LikedPhotos
import com.playrix.gar.some_services.converters.RoomBitmapConverter

@Database(entities = [LikedPhotos::class], version = 1, exportSchema = false)
@TypeConverters(RoomBitmapConverter::class)
abstract class PhotosDatabase : RoomDatabase() {
    abstract fun likedPhotosDao(): LikedPhotosDao

    companion object {
        private const val databaseName: String = "database_name"

        @Volatile
        private var databaseInstance: PhotosDatabase? = null

        @Synchronized
        fun getDatabaseInstance(context: Context) =
            databaseInstance ?: synchronized(this) {
                databaseInstance ?: buildDatabase(
                    context
                ).also {
                    databaseInstance = it
                }
            }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            PhotosDatabase::class.java,
            databaseName
        ).build()
    }
}