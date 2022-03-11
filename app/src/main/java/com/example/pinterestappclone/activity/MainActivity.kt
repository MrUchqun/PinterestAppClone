package com.example.pinterestappclone.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.pinterestappclone.R
import com.example.pinterestappclone.fragment.ExploreFragment
import com.example.pinterestappclone.fragment.HomeFragment
import com.example.pinterestappclone.fragment.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    companion object {
        lateinit var bnvMain: BottomNavigationView
        const val profileMe =
            "https://images.unsplash.com/profile-fb-1646646690-6c984fc8f35c.jpg?dpr=1&auto=format&fit=crop&w=150&h=150&q=60&crop=faces&bg=fff"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(HomeFragment())
        initViews()
    }

    private fun initViews() {
        bnvMain = findViewById(R.id.bnv_main)
        bnvMain.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_home -> replaceFragment(HomeFragment())
                R.id.menu_search -> replaceFragment(SearchFragment())
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val backStateName = fragment.javaClass.name
        val manager: FragmentManager = supportFragmentManager
        val fragmentPopped: Boolean = manager.popBackStackImmediate(backStateName, 0)
        if (!fragmentPopped) { //fragment not in back stack, create it.
            val ft: FragmentTransaction = manager.beginTransaction()
            ft.replace(R.id.view_container, fragment)
            ft.addToBackStack(backStateName)
            ft.commit()
        }
    }


    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 1) {
            finish()
        } else {
            super.onBackPressed()
        }
    }
}