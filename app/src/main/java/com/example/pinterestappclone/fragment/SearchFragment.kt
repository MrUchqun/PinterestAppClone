package com.example.pinterestappclone.fragment

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.pinterestappclone.R
import com.example.pinterestappclone.adapter.IdeaPageAdapter
import com.example.pinterestappclone.adapter.PagerAdapter
import com.example.pinterestappclone.adapter.PopularAdapter
import com.example.pinterestappclone.model.PhotoList
import com.example.pinterestappclone.model.Topic
import com.example.pinterestappclone.network.RetrofitHttp
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList


class SearchFragment : Fragment() {

    companion object {
        const val LANDSCAPE = "landscape"
        const val PORTRAIT = "portrait"
        const val DELAY_MS: Long = 2500 //delay in milliseconds before task is to be executed
        const val PERIOD_MS: Long = 5000 // time in milliseconds between successive task executions.

        fun newInstance(): SearchFragment {
            return SearchFragment()
        }
    }

    private lateinit var ideasAdapter: IdeaPageAdapter
    private lateinit var popularAdapter: PopularAdapter
    private lateinit var pagerAdapter: PagerAdapter

    private lateinit var vpAds: ViewPager
    private lateinit var indicator: WormDotsIndicator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ideasAdapter = IdeaPageAdapter(requireContext())
        popularAdapter = PopularAdapter(requireContext())
        pagerAdapter = PagerAdapter(parentFragmentManager)

        apiRandomPhotos("science", PORTRAIT, 10, 1)
        apiTopics(1, 8)
    }

    override fun onResume() {
        super.onResume()
        apiRandomPhotos("products", LANDSCAPE, 7, 2)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
    }

    private fun initViews(view: View) {
        val tvSearch = view.findViewById<TextView>(R.id.tv_search)

        tvSearch.setOnClickListener {
            replaceFragment(SearchResultFragment.newInstance(null))
        }

        setupAds(view)
        refreshIdeasAdapter(view)
        refreshPopularAdapter(view)
    }

    private fun replaceFragment(fragment: Fragment) {
        val backStateName = fragment.javaClass.name
        val manager: FragmentManager = childFragmentManager
        val ft: FragmentTransaction = manager.beginTransaction()
        ft.replace(R.id.view_container, fragment)
        ft.addToBackStack(backStateName)
        ft.commit()
    }

    private fun setupAds(view: View) {
        vpAds = view.findViewById(R.id.vp_ads)
        indicator = view.findViewById(R.id.dots_indicator)
        automateViewPagerSwiping()
    }

    private fun refreshAdsAdapter() {
        vpAds.adapter = pagerAdapter
        indicator.setViewPager(vpAds)
    }

    private fun refreshIdeasAdapter(view: View) {
        val rvIdeas = view.findViewById<RecyclerView>(R.id.rv_ideas)
        rvIdeas.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rvIdeas.adapter = ideasAdapter
    }

    private fun refreshPopularAdapter(view: View) {
        val rvPopular = view.findViewById<RecyclerView>(R.id.rv_popular)
        rvPopular.layoutManager = GridLayoutManager(requireContext(), 2)
        rvPopular.adapter = popularAdapter
    }

    private fun automateViewPagerSwiping() {
        val handler = Handler()
        val update = Runnable {
            if (vpAds.currentItem == pagerAdapter.count - 1) {
                vpAds.currentItem = 0
            } else {
                vpAds.setCurrentItem(vpAds.currentItem + 1, true)
            }
        }
        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                handler.post(update)
            }
        }, DELAY_MS, PERIOD_MS)
    }

    /* functions for call network */

    private fun apiRandomPhotos(query: String, orientation: String, count: Int, number: Int) {
        RetrofitHttp.photoService.getRandomPhotos(query, orientation, count)
            .enqueue(object : Callback<PhotoList> {
                override fun onResponse(call: Call<PhotoList>, response: Response<PhotoList>) {
                    if (number == 1) {
                        ideasAdapter.addList(response.body()!!)
                    }

                    if (number == 2) {
                        if (pagerAdapter.fragments.size == 0) {
                            for (item in response.body()!!)
                                pagerAdapter.addFragment(AdsFragment.newInstance(item))
                        }
                        refreshAdsAdapter()
                    }
                }

                override fun onFailure(call: Call<PhotoList>, t: Throwable) {
                    Log.e("@@@", t.message.toString())
                }
            })
    }

    private fun apiTopics(page: Int, perPage: Int) {
        RetrofitHttp.photoService.getTopics(page, perPage)
            .enqueue(object : Callback<ArrayList<Topic>> {
                override fun onResponse(
                    call: Call<ArrayList<Topic>>,
                    response: Response<ArrayList<Topic>>
                ) {
                    popularAdapter.addPhotos(response.body()!!)
                }

                override fun onFailure(call: Call<ArrayList<Topic>>, t: Throwable) {
                    Log.e("@@@", t.message.toString())
                }
            })
    }

}