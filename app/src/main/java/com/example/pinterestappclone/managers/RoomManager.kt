package com.example.pinterestappclone.managers

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pinterestappclone.database.PhotoTypeConverter
import com.example.pinterestappclone.database.PinDao
import com.example.pinterestappclone.model.Pin

@Database(entities = [Pin::class], version = 1)
@TypeConverters(PhotoTypeConverter::class)
abstract class RoomManager : RoomDatabase() {

    abstract fun pinDao(): PinDao

    companion object {

        private var INSTANCE: RoomManager? = null

        @Synchronized
        fun getInstance(context: Context): RoomManager {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context, RoomManager::class.java, "saved_photos_dp")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }

            return INSTANCE!!
        }

    }

}