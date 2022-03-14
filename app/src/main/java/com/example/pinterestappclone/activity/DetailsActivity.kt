package com.example.pinterestappclone.activity

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewParent
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.example.pinterestappclone.R
import com.example.pinterestappclone.adapter.PagerAdapter
import com.example.pinterestappclone.fragment.DetailsFragment
import com.example.pinterestappclone.fragment.HomeFragment
import com.example.pinterestappclone.managers.PrefsManager
import com.example.pinterestappclone.model.PhotoItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class DetailsActivity : AppCompatActivity() {

    private var prefsManager: PrefsManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBarColor()
        setContentView(R.layout.activity_details)
        prefsManager = PrefsManager.getInstance(this)
        initViews()
    }

    private fun initViews() {
        val vpDetails = findViewById<ViewPager>(R.id.vp_details)
        refreshAdapter(vpDetails, getList(), getPosition())
    }

    private fun refreshAdapter(
        viewPager: ViewPager,
        photoList: ArrayList<PhotoItem>,
        position: Int
    ) {
        val adapter = PagerAdapter(supportFragmentManager)
        for (photoItem in photoList) {
            adapter.addFragment(DetailsFragment.newInstance(photoItem))
        }
        viewPager.adapter = adapter
        viewPager.currentItem = position
    }

    private fun getList(): ArrayList<PhotoItem> {
        val type: Type = object : TypeToken<ArrayList<PhotoItem>>() {}.type
        val photoList = prefsManager!!.getArrayList<PhotoItem>(PrefsManager.KEY_PHOTO_LIST, type)
        prefsManager!!.removeData(PrefsManager.KEY_PHOTO_LIST)
        return photoList
    }

    private fun getPosition(): Int {
        return intent.getIntExtra("position", 0)
    }

    private fun setStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val decor = this.window.decorView
            decor.systemUiVisibility = 0
        }
        window.statusBarColor = Color.BLACK
    }

}