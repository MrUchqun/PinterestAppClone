package com.example.pinterestappclone.activity

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.pinterestappclone.R
import com.example.pinterestappclone.adapter.PagerAdapter
import com.example.pinterestappclone.custom.NoSwipePager
import com.example.pinterestappclone.fragment.*
import com.example.pinterestappclone.managers.PrefsManager
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var vpMain: NoSwipePager
    private var memoryList = ArrayList<Int>()

    companion object {
        const val profileMe =
            "https://images.unsplash.com/profile-fb-1646646690-6c984fc8f35c.jpg?dpr=1&auto=format&fit=crop&w=150&h=150&q=60&crop=faces&bg=fff"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        memoryList.add(0)
    }

    var prevMenuItem: MenuItem? = null
    private fun initViews() {
        val bnvMain: BottomNavigationView = findViewById(R.id.bnv_main)

        vpMain = findViewById(R.id.vp_main)
        vpMain.adapter = setupAdapter()
        connectionVpWithBnv(bnvMain, vpMain)
    }

    private fun setupAdapter(): PagerAdapter {
        val adapter = PagerAdapter(supportFragmentManager)
        adapter.addFragment(HomeFragment.newInstance())
        adapter.addFragment(SearchFragment.newInstance())
        adapter.addFragment(CommentFragment.newInstance())
        adapter.addFragment(MyProfileFragment.newInstance())
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

                addLastPage(position)
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

    fun addLastPage(page: Int) {
        if (page != 0) {
            if (memoryList.contains(page)) {
                memoryList.remove(page)
                memoryList.add(page)
            } else {
                memoryList.add(page)
            }
        }
    }

    override fun onBackPressed() {
        if (vpMain.currentItem != 0) {
            memoryList.remove(memoryList[memoryList.size - 1])
            vpMain.currentItem = memoryList[memoryList.size - 1]

        } else {
            super.onBackPressed()
        }
    }

}