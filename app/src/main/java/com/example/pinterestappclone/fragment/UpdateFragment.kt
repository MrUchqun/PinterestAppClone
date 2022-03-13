package com.example.pinterestappclone.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pinterestappclone.R
import com.example.pinterestappclone.adapter.UpdatesAdapter
import com.example.pinterestappclone.model.Topic
import com.example.pinterestappclone.network.RetrofitHttp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateFragment : Fragment() {

    private lateinit var updatesAdapter: UpdatesAdapter
    private lateinit var rvUpdates: RecyclerView
    private var currentPage = 1
    private var perPage = 15

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        updatesAdapter = UpdatesAdapter(requireContext())
        apiTopics(currentPage++, perPage)
        Log.d("@@@UpdateFragment", "onCreate: ")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("@@@UpdateFragment", "onCreateView: ")
        return inflater.inflate(R.layout.fragment_update_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("@@@UpdateFragment", "onViewCreated: ")
        initViews(view)
    }

    private fun initViews(view: View) {
        rvUpdates = view.findViewById(R.id.rv_updates)
        rvUpdates.layoutManager = LinearLayoutManager(requireContext())
        rvUpdates.adapter = updatesAdapter
        rvUpdates.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!rvUpdates.canScrollVertically(1) && currentPage < 4) {
                    apiTopics(currentPage++, perPage)
                }
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        (rvUpdates.adapter as UpdatesAdapter).notifyDataSetChanged()
        Log.d("@@@UpdateFragment", "onResume: ")
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("@@@UpdateFragment", "onAttach: ")
    }

    override fun onStart() {
        super.onStart()
        Log.d("@@@UpdateFragment", "onStart: ")
    }

    override fun onPause() {
        super.onPause()
        Log.d("@@@UpdateFragment", "onPause: ")
    }

    override fun onStop() {
        super.onStop()
        Log.d("@@@UpdateFragment", "onStop: ")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("@@@UpdateFragment", "onDestroyView: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("@@@UpdateFragment", "onDestroy: ")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("@@@UpdateFragment", "onDetach: ")
    }

    private fun apiTopics(page: Int, perPage: Int) {
        RetrofitHttp.photoService.getTopics(page, perPage)
            .enqueue(object : Callback<ArrayList<Topic>> {
                override fun onResponse(
                    call: Call<ArrayList<Topic>>,
                    response: Response<ArrayList<Topic>>
                ) {
                    updatesAdapter.addTopics(response.body()!!)
                }

                override fun onFailure(call: Call<ArrayList<Topic>>, t: Throwable) {
                    Log.e("@@@", t.message.toString())
                }
            })
    }
}