package com.example.pinterestappclone.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.pinterestappclone.R
import com.example.pinterestappclone.adapter.PagerAdapter
import com.example.pinterestappclone.model.PhotoItem
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson

class CommentFragment : Fragment() {

    companion object {
        fun newInstance(): CommentFragment {
            return CommentFragment()
        }
    }

    private lateinit var pagerAdapter: PagerAdapter
    private lateinit var vpFilter: ViewPager
    private lateinit var tlFilter: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_comment_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
    }

    private fun initViews(view: View) {
        tlFilter = view.findViewById(R.id.tl_filter)
        vpFilter = view.findViewById(R.id.vp_filter)
        val ivParams = view.findViewById<ImageView>(R.id.iv_params)

        refreshAdapter()
        changeIconVisible(ivParams, vpFilter.currentItem)

        vpFilter.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                changeIconVisible(ivParams, position)
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

    override fun onResume() {
        super.onResume()
        setAdapter()
        refreshAdapter()
    }

    private fun changeIconVisible(view: View, position: Int) {
        view.visibility = if (position == 0) VISIBLE else INVISIBLE
    }

    private fun setAdapter() {
        pagerAdapter = PagerAdapter(childFragmentManager)
        pagerAdapter.addFragment(UpdateFragment.newInstance())
        pagerAdapter.addFragment(MessagesFragment.newInstance())
        pagerAdapter.addTitle(getString(R.string.tab_updates))
        pagerAdapter.addTitle(getString(R.string.tab_messages))
    }

    private fun refreshAdapter() {
        vpFilter.adapter = pagerAdapter
        tlFilter.setupWithViewPager(vpFilter)
    }
}

