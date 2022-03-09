package com.example.pinterestappclone.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.airbnb.lottie.LottieAnimationView
import com.example.pinterestappclone.R
import com.example.pinterestappclone.activity.MainActivity
import com.example.pinterestappclone.adapter.PhotosAdapter
import com.example.pinterestappclone.model.PhotoList
import com.example.pinterestappclone.network.RetrofitHttp
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type

class HomeFragment : Fragment() {

    private lateinit var adapter: PhotosAdapter
    private var currentPage = 1
    private var perPage = 20

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home_page, container, false)
        initView(view)
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        apiPhotoList()
    }

    private fun initView(view: View) {
        val rvHome = view.findViewById<RecyclerView>(R.id.rv_home)
        rvHome.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        refreshAdapter(rvHome)
        rvHome.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!rvHome.canScrollVertically(1)) {
                    apiPhotoList()
                }
            }
        })
    }

    private fun refreshAdapter(recyclerView: RecyclerView) {
        adapter = PhotosAdapter(requireContext())
        recyclerView.adapter = adapter
    }

    private fun apiPhotoList() {
        RetrofitHttp.photoService.getPhotos(++currentPage, perPage)
            .enqueue(object : Callback<PhotoList> {
                override fun onResponse(call: Call<PhotoList>, response: Response<PhotoList>) {
                    adapter.addPhotos(response.body()!!)
                }

                override fun onFailure(call: Call<PhotoList>, t: Throwable) {
                    Log.e("@@@", t.message.toString())
                    Log.e("@@@", t.toString())
                }
            })
    }
}