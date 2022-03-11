package com.example.pinterestappclone.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.pinterestappclone.R
import com.example.pinterestappclone.adapter.ResultPhotosAdapter
import com.example.pinterestappclone.model.ResultPhotos
import com.example.pinterestappclone.network.RetrofitHttp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfilesFragment(var text: String) : Fragment() {

    private lateinit var adapter: ResultPhotosAdapter
    private var currentPage = 1
    private val perPage = 20

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

    private fun initViews(view: View) {
        val rvSearch = view.findViewById<RecyclerView>(R.id.rv_search)
        rvSearch.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        rvSearch.adapter = adapter
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

}