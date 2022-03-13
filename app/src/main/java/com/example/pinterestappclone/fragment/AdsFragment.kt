package com.example.pinterestappclone.fragment

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
import com.example.pinterestappclone.model.PhotoItem

class AdsFragment(var photoItem: PhotoItem) : Fragment() {

    private lateinit var ivAds: ImageView
    private lateinit var tvDescription: TextView

    override fun onResume() {
        super.onResume()

        Glide.with(requireContext()).load(photoItem.urls!!.regular)
            .placeholder(ColorDrawable(Color.parseColor(photoItem.color)))
            .into(ivAds)

        if (!photoItem.description.isNullOrEmpty()) {
            tvDescription.text = photoItem.description
        }
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
        tvDescription = view.findViewById(R.id.tv_description)
    }

}