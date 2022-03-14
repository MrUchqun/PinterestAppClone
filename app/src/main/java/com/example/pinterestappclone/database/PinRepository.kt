package com.example.pinterestappclone.database

import android.app.Application
import com.example.pinterestappclone.managers.RoomManager
import com.example.pinterestappclone.model.PhotoItem
import com.example.pinterestappclone.model.Pin

class PinRepository(application: Application) {

    private val dp = RoomManager.getInstance(application)
    private var pinDao = dp.pinDao()

    fun savePhoto(pin: Pin) {
        pinDao.savePhoto(pin)
    }

    fun getAllSavedPhotos(): List<Pin> {
        return pinDao.getAllSavedPhotos()
    }

    fun clearSavedPhotos() {
        pinDao.clearSavedPhotos()
    }

    fun removeSavedPhotos(id: Int) {
        pinDao.removeSavedPhotos(id)
    }

}