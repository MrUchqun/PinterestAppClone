package com.example.pinterestappclone.fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.pinterestappclone.R
import com.example.pinterestappclone.model.PhotoItem
import com.example.pinterestappclone.model.PhotoList
import com.example.pinterestappclone.network.RetrofitHttp
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdsFragment : Fragment() {

    companion object {
        const val LANDSCAPE = "landscape"
        const val PORTRAIT = "portrait"
    }

    private lateinit var ivAds: ImageView

    override fun onResume() {
        super.onResume()
        apiRandomPhoto()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ads, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ivAds = view.findViewById(R.id.iv_ads)
    }

    private fun apiRandomPhoto() {
        RetrofitHttp.photoService.getRandomPhotos("product", LANDSCAPE, 1)
            .enqueue(object : Callback<PhotoList> {
                override fun onResponse(call: Call<PhotoList>, response: Response<PhotoList>) {
                    Picasso.get().load(response.body()!![0].user!!.profile_image!!.medium)
                        .placeholder(ColorDrawable(Color.parseColor(response.body()!![0].color)))
                        .into(ivAds)
                }

                override fun onFailure(call: Call<PhotoList>, t: Throwable) {
                    Log.e("@@@", t.message.toString())
                }
            })
    }

}