package com.example.pinterestappclone.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.pinterestappclone.model.Pin

@Dao
interface PinDao {

    @Insert
    fun savePhoto(pins: Pin)

    @Query("SELECT * FROM pins_table")
    fun getAllSavedPhotos(): List<Pin>

    @Query("DELETE FROM pins_table")
    fun clearSavedPhotos()

    @Query("DELETE FROM pins_table WHERE id=:id")
    fun removeSavedPhotos(id: Int)

}