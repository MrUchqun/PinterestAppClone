package com.example.pinterestappclone.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
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

class ProfilesFragment : Fragment() {

    companion object {
        private const val KEY_STRING = "string"
        fun newInstance(text: String): ProfilesFragment {
            val args = Bundle()
            args.putString(KEY_STRING, text)
            val newFragment = ProfilesFragment()
            newFragment.arguments = args
            return newFragment
        }
    }

    private lateinit var rvSearch: RecyclerView
    private lateinit var adapter: SearchProfileAdapter
    private var currentPage = 1
    private val perPage = 20

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = SearchProfileAdapter(requireContext())
        apiSearchProfiles()
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
        rvSearch.layoutManager = LinearLayoutManager(requireContext())
        rvSearch.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!rvSearch.canScrollVertically(1)) {
                    apiSearchProfiles()
                }
            }
        })
    }

    private fun apiSearchProfiles() {
        val text = arguments?.getString(KEY_STRING)!!
        RetrofitHttp.photoService.getSearchProfile(currentPage++, text, perPage)
            .enqueue(object : Callback<ResultProfiles> {
                override fun onResponse(
                    call: Call<ResultProfiles>,
                    response: Response<ResultProfiles>
                ) {
                    adapter.addProfiles(response.body()!!.results!!)
                }

                override fun onFailure(call: Call<ResultProfiles>, t: Throwable) {
                    Log.e("@@@", t.message.toString())
                    Log.e("@@@", t.toString())
                }
            })
    }

}