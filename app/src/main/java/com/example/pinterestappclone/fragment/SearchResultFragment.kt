package com.example.pinterestappclone.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.pinterestappclone.R
import com.example.pinterestappclone.adapter.HelperTextAdapter
import com.example.pinterestappclone.adapter.ResultPhotosAdapter
import com.example.pinterestappclone.managers.PrefsManager
import com.example.pinterestappclone.model.ResultPhotos
import com.example.pinterestappclone.network.RetrofitHttp
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type

class SearchResultFragment(var text: String) : Fragment() {

    private lateinit var prefsManager: PrefsManager
    private lateinit var helperAdapter: HelperTextAdapter


    private lateinit var adapter: ResultPhotosAdapter
    private var currentPage = 1
    private val perPage = 20

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefsManager = PrefsManager.getInstance(requireContext())!!
        helperAdapter = HelperTextAdapter(requireContext(), getHistory())
        adapter = ResultPhotosAdapter(requireContext())
    }

    override fun onResume() {
        super.onResume()
        apiSearchPhotos(text)
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
        val rvSearch = view.findViewById<RecyclerView>(R.id.rv_search)
        val etSearch = view.findViewById<EditText>(R.id.et_search)
        val ivBtnBack = view.findViewById<ImageView>(R.id.iv_btn_back)

        ivBtnBack.setOnClickListener {
            parentFragmentManager.beginTransaction().remove(this).commit()
            parentFragmentManager.popBackStack()
            if (parentFragmentManager.backStackEntryCount > 1) {
                parentFragmentManager.popBackStack()
            }
        }

        etSearch.setText(text)

        etSearch.setOnEditorActionListener { v, actionId, event ->
            if (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER || actionId == EditorInfo.IME_ACTION_DONE) {
                if (etSearch.text.isNotEmpty() && etSearch.text.isNotBlank()) {
                    val text = etSearch.text.toString()
                    replaceFragment(SearchResultFragment(text))
                }
            }
            false
        }

        rvSearch.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        rvSearch.adapter = adapter

        rvSearch.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) {
                    apiSearchPhotos(text)
                }
            }
        })
    }

    private fun replaceFragment(fragment: Fragment) {
        val backStateName = fragment.javaClass.name
        val manager: FragmentManager = parentFragmentManager
        val ft: FragmentTransaction = manager.beginTransaction()
        ft.replace(R.id.view_container, fragment)
        ft.addToBackStack(backStateName)
        ft.commit()
    }

    private fun apiSearchPhotos(text: String) {
        RetrofitHttp.photoService.getSearchPhoto(currentPage++, text, perPage)
            .enqueue(object : Callback<ResultPhotos> {
                override fun onResponse(
                    call: Call<ResultPhotos>,
                    response: Response<ResultPhotos>
                ) {
                    adapter.addPhotos(response.body()!!.results!!)
                    Log.d("@@@", "Response = " + response.body()!!.total.toString())
                }

                override fun onFailure(call: Call<ResultPhotos>, t: Throwable) {
                    Log.e("@@@", t.message.toString())
                    Log.e("@@@", t.toString())
                }
            })
    }

    private fun inVisibleCursor(etSearch: EditText, v: View) {
        val outRect = Rect()
        etSearch.getGlobalVisibleRect(outRect)
        etSearch.clearFocus()

        /* Keyboard hide command */
        val imm: InputMethodManager =
            v.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(v.windowToken, 0)
    }

    private fun getHistory(): ArrayList<String> {
        val type: Type = object : TypeToken<ArrayList<String>>() {}.type
        return prefsManager.getArrayList(PrefsManager.KEY_LIST, type)
    }


    /*private fun refreshAdapter(viewPager: ViewPager, tabLayout: TabLayout, text: String) {
    val pagerAdapter = PagerAdapter(requireActivity().supportFragmentManager)
    pagerAdapter.addFragment(ExploreFragment(text))

    pagerAdapter.addTitle(getString(R.string.tab_explore))
    pagerAdapter.addFragment(ProfilesFragment(text))

    pagerAdapter.addTitle(getString(R.string.tab_profiles))
    viewPager.adapter = pagerAdapter

    tabLayout.setupWithViewPager(viewPager)
}*/
}