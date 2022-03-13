package com.example.pinterestappclone.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.viewpager.widget.ViewPager
import com.example.pinterestappclone.R
import com.example.pinterestappclone.adapter.HelperTextAdapter
import com.example.pinterestappclone.adapter.PagerAdapter
import com.example.pinterestappclone.adapter.PhotosAdapter
import com.example.pinterestappclone.adapter.ResultPhotosAdapter
import com.example.pinterestappclone.managers.PrefsManager
import com.example.pinterestappclone.model.ResultPhotos
import com.example.pinterestappclone.network.RetrofitHttp
import com.google.android.material.tabs.TabLayout
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type

class SearchResultFragment(private val textParent: String?) : Fragment() {

    private lateinit var prefsManager: PrefsManager
    private lateinit var helperAdapter: HelperTextAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // initialize SharedPreferences manager
        prefsManager = PrefsManager.getInstance(requireContext())!!

        // initialize adapter of rvHelper
        helperAdapter = HelperTextAdapter(requireContext(), getHistory())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_result_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initViews(view: View) {

        // initialize all view elements
        val ivBtnBack = view.findViewById<ImageView>(R.id.iv_btn_back)
        val etSearch = view.findViewById<EditText>(R.id.et_search)
        val rvHelper = view.findViewById<RecyclerView>(R.id.rv_helper)
        val tvCancel = view.findViewById<TextView>(R.id.tv_cancel)
        val llContainer = view.findViewById<LinearLayout>(R.id.ll_container)
        val tlFilter = view.findViewById<TabLayout>(R.id.tl_filter)
        val vpFilter = view.findViewById<ViewPager>(R.id.vp_filter)

        // set layout manager for recyclerview
        rvHelper.layoutManager = LinearLayoutManager(context)

        if (textParent.isNullOrEmpty()) {

            // gone back button
            ivBtnBack.visibility = GONE

            // show cancel button
            tvCancel.visibility = VISIBLE

            // show rvHelper
            refreshAdapter(rvHelper)

            // focusable etSearch
            etSearch.showKeyboard()

        } else {

            // hide keyboard
            hideKeyboardFrom(requireContext(), view)

            // hide cancel button
            tvCancel.visibility = GONE

            // show back button
            ivBtnBack.visibility = VISIBLE

            // set text on etSearch
            etSearch.setText(textParent)

            // open search page
            visibleViewPager(rvHelper, llContainer, vpFilter, tlFilter, textParent)
        }

        // set cancel button function when clicked
        tvCancel.setOnClickListener {
            clearFragments()
        }

        // set back button function when clicked
        ivBtnBack.setOnClickListener {
            removeFragment(this)
        }

        etSearch.setOnEditorActionListener { _, actionId, event ->

            // check keyboard clicked
            if (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER || actionId == EditorInfo.IME_ACTION_DONE) {

                // check etSearch text not empty
                if (etSearch.text.isNotEmpty() && etSearch.text.isNotBlank()) {

                    val text = etSearch.text.toString()

                    // add last search text to prefs manager
                    helperAdapter.addHelper(text)

                    // hide keyboard
                    hideKeyboardFrom(requireContext(), view)

                    // back button show
                    ivBtnBack.visibility = VISIBLE

                    // cancel button hide
                    tvCancel.visibility = GONE

                    // open search page
                    visibleViewPager(rvHelper, llContainer, vpFilter, tlFilter, text)
                }
            }
            false
        }

        // open new fragment for research
        etSearch.setOnTouchListener { _, _ ->
            replaceFragment(SearchResultFragment(etSearch.text.toString()))
            false
        }
    }

    private fun refreshAdapter(recyclerView: RecyclerView) {
        recyclerView.visibility = VISIBLE
        recyclerView.adapter = helperAdapter
    }

    private fun replaceFragment(fragment: Fragment) {
        val backStateName = fragment.javaClass.name
        val manager: FragmentManager = parentFragmentManager
        val ft: FragmentTransaction = manager.beginTransaction()
        ft.replace(R.id.view_container, fragment)
        ft.addToBackStack(backStateName)
        ft.commit()
    }

    private fun removeFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction().remove(fragment).commit()
        parentFragmentManager.popBackStack()
    }

    private fun clearFragments() {
        val backStateName = this.javaClass.name
        parentFragmentManager.popBackStack(backStateName, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    private fun EditText.showKeyboard() {
        if (requestFocus()) {
            (activity?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager)
                .showSoftInput(this, SHOW_IMPLICIT)
            setSelection(text.length)
        }
    }

    private fun hideKeyboardFrom(context: Context, view: View) {
        val imm = context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun getHistory(): ArrayList<String> {
        val type: Type = object : TypeToken<ArrayList<String>>() {}.type
        return prefsManager.getArrayList(PrefsManager.KEY_LIST, type)
    }

    private fun visibleViewPager(
        recyclerView: RecyclerView,
        linearLayout: LinearLayout,
        viewPager: ViewPager,
        tabLayout: TabLayout,
        text: String?
    ) {
        if (!text.isNullOrEmpty()) {

            recyclerView.visibility = GONE
            linearLayout.visibility = VISIBLE

            val pagerAdapter = PagerAdapter(parentFragmentManager)
            pagerAdapter.addFragment(ExploreFragment(text))
            pagerAdapter.addTitle(getString(R.string.tab_explore))

            pagerAdapter.addFragment(ProfilesFragment(text))
            pagerAdapter.addTitle(getString(R.string.tab_profiles))

            viewPager.adapter = pagerAdapter
            tabLayout.setupWithViewPager(viewPager)
        }
    }

}