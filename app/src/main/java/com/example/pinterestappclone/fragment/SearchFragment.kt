package com.example.pinterestappclone.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.View.*
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.pinterestappclone.R
import com.example.pinterestappclone.activity.MainActivity
import com.example.pinterestappclone.adapter.HelperTextAdapter
import com.example.pinterestappclone.adapter.IdeaPageAdapter
import com.example.pinterestappclone.adapter.PagerAdapter
import com.example.pinterestappclone.adapter.ResultPhotosAdapter
import com.example.pinterestappclone.fragment.AdsFragment.Companion.PORTRAIT
import com.example.pinterestappclone.managers.PrefsManager
import com.example.pinterestappclone.model.PhotoList
import com.example.pinterestappclone.model.ResultPhotos
import com.example.pinterestappclone.network.RetrofitHttp
import com.google.android.material.tabs.TabLayout
import com.google.gson.reflect.TypeToken
import com.squareup.picasso.Picasso
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type


class SearchFragment : Fragment() {

    private lateinit var prefsManager: PrefsManager
    private lateinit var helperAdapter: HelperTextAdapter
    private lateinit var ideasAdapter: IdeaPageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefsManager = PrefsManager.getInstance(requireContext())!!
        helperAdapter = HelperTextAdapter(requireContext(), getHistory())
        apiRandomPhotos()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search_page, container, false)
        initViews(view)
        return view
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initViews(view: View) {
        val etSearch = view.findViewById<EditText>(R.id.et_search)
        val ivSearch = view.findViewById<ImageView>(R.id.iv_ic_search)
        val ivClearTxt = view.findViewById<ImageView>(R.id.iv_clear_text)
        val tvCancel = view.findViewById<TextView>(R.id.tv_cancel)

        val vpAds = view.findViewById<ViewPager>(R.id.vp_ads)
        val pagerAdapter = PagerAdapter(requireActivity().supportFragmentManager)
        for (i in 0..7)
            pagerAdapter.addFragment(AdsFragment())
        vpAds.adapter = pagerAdapter
        val indicator = view.findViewById<WormDotsIndicator>(R.id.dots_indicator)
        indicator.setViewPager(vpAds)

        val rvIdeas = view.findViewById<RecyclerView>(R.id.rv_ideas)
        rvIdeas.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rvIdeas.adapter = ideasAdapter

        val rvHelper = view.findViewById<RecyclerView>(R.id.rv_helper)
        rvHelper.layoutManager = GridLayoutManager(requireContext(), 1)


        etSearch.setOnTouchListener { _, _ ->
            ivSearch.visibility = GONE
            rvHelper.visibility = VISIBLE
            rvHelper.adapter = helperAdapter
            false
        }

        etSearch.setOnEditorActionListener { v, actionId, event ->
            if (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER || actionId == EditorInfo.IME_ACTION_DONE) {
                if (etSearch.text.isNotEmpty() && etSearch.text.isNotBlank()) {
                    val text = etSearch.text.toString()
                    helperAdapter.addHelper(text)
                    replaceFragment(ExploreFragment(text))
                }
            }
            false
        }

        etSearch.addTextChangedListener {
            ivClearTxt.visibility = if (etSearch.text.isNotEmpty()) VISIBLE
            else INVISIBLE
        }

        ivClearTxt.setOnClickListener {
            etSearch.text.clear()
        }

        rvHelper.setOnTouchListener { _, _ ->
            ivSearch.visibility = VISIBLE
            inVisibleCursor(etSearch, rvHelper)
            false
        }
    }

    private fun apiRandomPhotos() {
        RetrofitHttp.photoService.getRandomPhotos("news", PORTRAIT, 8)
            .enqueue(object : Callback<PhotoList> {
                override fun onResponse(call: Call<PhotoList>, response: Response<PhotoList>) {
                    ideasAdapter = IdeaPageAdapter(requireContext(), response.body()!!)
                }

                override fun onFailure(call: Call<PhotoList>, t: Throwable) {
                    Log.e("@@@", t.message.toString())
                }
            })
    }

    private fun replaceFragment(fragment: Fragment) {
        val backStateName = fragment.javaClass.name
        val manager: FragmentManager = requireActivity().supportFragmentManager
        val fragmentPopped: Boolean = manager.popBackStackImmediate(backStateName, 0)
        if (!fragmentPopped) { //fragment not in back stack, create it.
            val ft: FragmentTransaction = manager.beginTransaction()
            ft.replace(R.id.view_container, fragment)
            ft.addToBackStack(backStateName)
            ft.commit()
        }
    }

    private fun getHistory(): ArrayList<String> {
        val type: Type = object : TypeToken<ArrayList<String>>() {}.type
        return prefsManager.getArrayList(PrefsManager.KEY_LIST, type)
    }

    private fun refreshAdapter(viewPager: ViewPager, tabLayout: TabLayout, text: String) {
        val pagerAdapter = PagerAdapter(requireActivity().supportFragmentManager)
        pagerAdapter.addFragment(ExploreFragment(text))

        pagerAdapter.addTitle(getString(R.string.tab_explore))
        pagerAdapter.addFragment(ProfilesFragment(text))

        pagerAdapter.addTitle(getString(R.string.tab_profiles))
        viewPager.adapter = pagerAdapter

        tabLayout.setupWithViewPager(viewPager)
    }

    private fun inVisibleCursor(etSearch: EditText, v: View) {
        val outRect = Rect()
        etSearch.getGlobalVisibleRect(outRect)
        etSearch.clearFocus()

        //Keyboard hide command
        val imm: InputMethodManager =
            v.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(v.windowToken, 0)
    }


}