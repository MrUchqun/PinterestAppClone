package com.example.pinterestappclone.activity

import android.os.Bundle
import android.view.KeyEvent
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.pinterestappclone.R
import com.example.pinterestappclone.adapter.PagerAdapter
import com.example.pinterestappclone.custom.NoSwipePager
import com.example.pinterestappclone.fragment.HomeFragment
import com.example.pinterestappclone.fragment.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var vpMain: NoSwipePager

    companion object {
        const val profileMe =
            "https://images.unsplash.com/profile-fb-1646646690-6c984fc8f35c.jpg?dpr=1&auto=format&fit=crop&w=150&h=150&q=60&crop=faces&bg=fff"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    var prevMenuItem: MenuItem? = null
    private fun initViews() {
        vpMain = findViewById(R.id.vp_main)
        val bnvMain: BottomNavigationView = findViewById(R.id.bnv_main)

        vpMain.adapter = setupAdapter()

        connectionVpWithBnv(bnvMain, vpMain)
    }

    private fun setupAdapter(): PagerAdapter {
        val adapter = PagerAdapter(supportFragmentManager)
        adapter.addFragment(HomeFragment())
        adapter.addFragment(SearchFragment())
        return adapter
    }

    private fun connectionVpWithBnv(bnvMain: BottomNavigationView, vpMain: ViewPager) {
        bnvMain.setOnNavigationItemSelectedListener {
            vpMain.currentItem =
                when (it.itemId) {
                    R.id.menu_home -> 0
                    R.id.menu_search -> 1
                    R.id.menu_comment -> 2
                    else -> 3
                }
            false
        }

        vpMain.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                if (prevMenuItem != null) {
                    prevMenuItem?.isChecked = false
                } else {
                    bnvMain.menu.getItem(0).isChecked = false
                }

                bnvMain.menu.getItem(position).isChecked = true
                prevMenuItem = bnvMain.menu.getItem(position)
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (
            keyCode == KeyEvent.KEYCODE_BACK &&
            vpMain.currentItem == 1
        ) {
            vpMain.currentItem = 0
            true
        } else {
            super.onKeyDown(keyCode, event)
        }
    }
}