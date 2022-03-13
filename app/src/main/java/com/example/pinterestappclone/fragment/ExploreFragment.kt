package com.example.pinterestappclone.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.pinterestappclone.R
import com.example.pinterestappclone.adapter.PhotosAdapter
import com.example.pinterestappclone.adapter.ResultPhotosAdapter
import com.example.pinterestappclone.adapter.SearchProfileAdapter
import com.example.pinterestappclone.model.ResultPhotos
import com.example.pinterestappclone.model.ResultProfiles
import com.example.pinterestappclone.network.RetrofitHttp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExploreFragment(var text: String) : Fragment() {

    private lateinit var rvSearch: RecyclerView
    private lateinit var adapter: ResultPhotosAdapter
    private var currentPage = 1
    private var perPage = 20

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = ResultPhotosAdapter(requireContext())
        apiSearchPhotos()
    }

    override fun onResume() {
        super.onResume()
        rvSearch.adapter = adapter
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_explore, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
    }

    private fun initViews(view: View) {
        rvSearch = view.findViewById(R.id.rv_search)
        rvSearch.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        rvSearch.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!rvSearch.canScrollVertically(1)) {
                    apiSearchPhotos()
                }
            }
        })
    }

    private fun apiSearchPhotos() {
        RetrofitHttp.photoService.getSearchPhoto(currentPage++, text, perPage)
            .enqueue(object : Callback<ResultPhotos> {
                override fun onResponse(
                    call: Call<ResultPhotos>,
                    response: Response<ResultPhotos>
                ) {
                    adapter.addPhotos(response.body()!!.results!!)
                }

                override fun onFailure(call: Call<ResultPhotos>, t: Throwable) {
                    Log.e("@@@", t.message.toString())
                    Log.e("@@@", t.toString())
                }
            })
    }

}