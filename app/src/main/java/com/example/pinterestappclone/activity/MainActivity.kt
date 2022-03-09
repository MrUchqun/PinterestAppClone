package com.example.pinterestappclone.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.example.pinterestappclone.R
import com.example.pinterestappclone.fragment.HomeFragment
import com.example.pinterestappclone.model.PhotoList
import com.example.pinterestappclone.network.RetrofitHttp
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    companion object {
        const val FIRST_PAGE = 1
        const val PER_PAGE = 20
    }

    private lateinit var bnvMain: BottomNavigationView
    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    private fun initViews() {
        context = this
        bnvMain = findViewById(R.id.bnv_main)
        replaceFragment(HomeFragment())
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.view_container, fragment).commit()
    }

    /*private fun getPhotoList(fragment: HomeFragment) {
        RetrofitHttp.photoService.getPhotos(FIRST_PAGE, PER_PAGE)
            .enqueue(object : Callback<PhotoList> {
                override fun onResponse(call: Call<PhotoList>, response: Response<PhotoList>) {
                    replaceFragment(fragment.newInstance(response.body()!!))
                    Log.d("@@@","Into onResponse")
                }

                override fun onFailure(call: Call<PhotoList>, t: Throwable) {
                    Log.e("@@@", t.message.toString())
                    Log.e("@@@", t.toString())
                }
            })
        Log.d("@@@", "Exit Retrofit")
    }*/
}