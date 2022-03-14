package com.example.pinterestappclone.fragment

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.example.pinterestappclone.R
import com.example.pinterestappclone.adapter.PhotosAdapter
import com.example.pinterestappclone.adapter.ResultPhotosAdapter
import com.example.pinterestappclone.database.PinRepository
import com.example.pinterestappclone.model.PhotoItem
//import com.example.pinterestappclone.database.SavedPhotoRepository
import com.example.pinterestappclone.model.profile.ProfileResp
import com.example.pinterestappclone.network.RetrofitHttp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.timer

class MyProfileFragment : Fragment() {

    companion object {
        fun newInstance(): MyProfileFragment {
            return MyProfileFragment()
        }
    }

    private lateinit var ivBackground: ImageView
    private lateinit var ivProfile: ImageView
    private lateinit var tvFullName: TextView
    private lateinit var tvUsername: TextView
    private lateinit var tvFollowers: TextView
    private lateinit var rvSavedPhotos: RecyclerView
    private lateinit var photosAdapter: ResultPhotosAdapter
    private lateinit var pinRepository: PinRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_profile_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        apiUser()
    }

    override fun onResume() {
        super.onResume()
        refreshAdapter()
    }

    private fun initViews(view: View) {
        ivBackground = view.findViewById(R.id.iv_background)
        ivProfile = view.findViewById(R.id.iv_profile)
        tvFullName = view.findViewById(R.id.tv_fullName)
        tvUsername = view.findViewById(R.id.tv_username)
        tvFollowers = view.findViewById(R.id.tv_followers)
        rvSavedPhotos = view.findViewById(R.id.rv_saved_photos)

        rvSavedPhotos.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        pinRepository = PinRepository(requireActivity().application)
        photosAdapter = ResultPhotosAdapter(requireContext())
        rvSavedPhotos.adapter = photosAdapter
    }

    private fun refreshAdapter() {
        val pinList = pinRepository.getAllSavedPhotos()
        val photoList = ArrayList<PhotoItem>()
        for (item in pinList) {
            item.photoItem.let { photoList.add(it) }
        }

        if (photoList.size > 0) {
            Glide.with(requireContext()).load(photoList.random()!!.urls!!.small)
                .placeholder(ColorDrawable(Color.GRAY)).centerCrop().into(ivBackground)
        }

        photosAdapter.addNewPhotos(photoList)
    }

    private fun apiUser() {
        RetrofitHttp.photoService.getUser("mruchqun").enqueue(object : Callback<ProfileResp> {
            override fun onResponse(call: Call<ProfileResp>, response: Response<ProfileResp>) {
                val profileResp = response.body()

                Glide.with(requireActivity()).load(profileResp?.profile_image?.large)
                    .placeholder(ColorDrawable(Color.GRAY)).into(ivProfile)

                tvFullName.text = profileResp?.name
                tvUsername.text = profileResp?.username
                "${profileResp?.followers_count} followers • ${profileResp?.following_count} following".also {
                    tvFollowers.text = it
                }

            }

            override fun onFailure(call: Call<ProfileResp>, t: Throwable) {
                Log.d("@@@", t.message.toString())
            }
        })
    }

    fun <E> ArrayList<E>.random(): E? = if (size > 0) get(Random().nextInt(size)) else null
}