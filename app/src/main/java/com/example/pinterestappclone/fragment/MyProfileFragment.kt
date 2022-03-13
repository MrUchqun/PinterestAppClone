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
import com.bumptech.glide.Glide
import com.example.pinterestappclone.R
import com.example.pinterestappclone.model.profile.ProfileResp
import com.example.pinterestappclone.network.RetrofitHttp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyProfileFragment : Fragment() {

    private lateinit var ivBackground: ImageView
    private lateinit var ivProfile: ImageView
    private lateinit var tvFullName: TextView
    private lateinit var tvUsername: TextView
    private lateinit var tvFollowers: TextView

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
    }

    private fun initViews(view: View) {
        ivBackground = view.findViewById(R.id.iv_background)
        ivProfile = view.findViewById(R.id.iv_profile)
        tvFullName = view.findViewById(R.id.tv_fullName)
        tvUsername = view.findViewById(R.id.tv_username)
        tvFollowers = view.findViewById(R.id.tv_followers)

        apiUser("mruchqun", requireContext())
    }

    private fun apiUser(username: String, context: Context) {
        RetrofitHttp.photoService.getUser(username).enqueue(object : Callback<ProfileResp> {
            override fun onResponse(call: Call<ProfileResp>, response: Response<ProfileResp>) {
                val profileResp = response.body()

                Glide.with(requireContext()).load(profileResp?.profile_image?.large)
                    .placeholder(ColorDrawable(Color.GRAY)).into(ivProfile)

                if (profileResp?.photos?.size!! > 0)
                    Glide.with(requireContext()).load(profileResp.photos?.get(0)?.urls?.small)
                        .placeholder(ColorDrawable(Color.GRAY)).centerCrop().into(ivBackground)

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
}