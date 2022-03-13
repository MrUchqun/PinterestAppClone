package com.example.pinterestappclone.managers

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class PrefsManager(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("MyPref", Context.MODE_PRIVATE)

    companion object {

        const val KEY_ARRAY_LIST = "arrayList"
        const val KEY_PHOTO_LIST = "photoList"

        private var prefsManager: PrefsManager? = null

        fun getInstance(context: Context): PrefsManager? {
            if (prefsManager == null) {
                prefsManager = PrefsManager(context)
            }
            return prefsManager
        }

    }

    fun <T> saveArrayList(key: String?, value: ArrayList<T>?) {
        val prefsEditor = sharedPreferences.edit()
        val json: String = Gson().toJson(value)
        prefsEditor.putString(key, json)
        prefsEditor.apply()
    }

    fun <T> getArrayList(key: String?, type: Type): ArrayList<T> {
        val json: String? = sharedPreferences.getString(key, null)
        return if (json != null) Gson().fromJson(json, type)
        else ArrayList()
    }

    fun removeData(key: String?) {
        val prefsEditor = sharedPreferences.edit()
        prefsEditor.remove(key)
        prefsEditor.apply()
    }
}