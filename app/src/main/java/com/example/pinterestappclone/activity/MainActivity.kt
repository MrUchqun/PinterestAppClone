package com.example.pinterestappclone.activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.pinterestappclone.R
import com.example.pinterestappclone.fragment.HomeFragment
import com.example.pinterestappclone.network.RetrofitHttp
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log


class MainActivity : AppCompatActivity() {

    lateinit var bnvMain: BottomNavigationView

    companion object {
        const val profileMe =
            "https://images.unsplash.com/profile-fb-1646646690-6c984fc8f35c.jpg?dpr=1&auto=format&fit=crop&w=150&h=150&q=60&crop=faces&bg=fff"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    private fun initViews() {
        bnvMain = findViewById(R.id.bnv_main)
        replaceFragment(HomeFragment())
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.view_container, fragment).commit()
    }

}