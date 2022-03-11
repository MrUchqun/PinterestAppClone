package com.example.pinterestappclone.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.pinterestappclone.R
import com.example.pinterestappclone.adapter.ResultPhotosAdapter
import com.example.pinterestappclone.model.ResultPhotos
import com.example.pinterestappclone.network.RetrofitHttp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExploreFragment(var text: String) : Fragment() {

    private lateinit var adapter: ResultPhotosAdapter
    private var currentPage = 1
    private val perPage = 20

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = ResultPhotosAdapter(requireContext())
    }

    override fun onResume() {
        super.onResume()
        apiSearchPhotos(text, true)
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

        etSearch.setText(text)

        etSearch.setOnEditorActionListener { v, actionId, event ->
            if (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER || actionId == EditorInfo.IME_ACTION_DONE) {
                if (etSearch.text.isNotEmpty() && etSearch.text.isNotBlank()) {
                    val text = etSearch.text.toString()
                    replaceFragment(ExploreFragment(text))
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
                    apiSearchPhotos(text, false)
                }
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

    private fun apiSearchPhotos(text: String, isNew: Boolean) {
        RetrofitHttp.photoService.getSearchPhoto(currentPage++, text, perPage)
            .enqueue(object : Callback<ResultPhotos> {
                override fun onResponse(
                    call: Call<ResultPhotos>,
                    response: Response<ResultPhotos>
                ) {
                    if (isNew) {
                        currentPage = 1
                        adapter.addNewPhotos(response.body()!!.results!!)
                    } else
                        adapter.addPhotos(response.body()!!.results!!)

                    Log.d("@@@", "Response = " + response.body()!!.total.toString())
                }

                override fun onFailure(call: Call<ResultPhotos>, t: Throwable) {
                    Log.e("@@@", t.message.toString())
                    Log.e("@@@", t.toString())
                }
            })
    }
}