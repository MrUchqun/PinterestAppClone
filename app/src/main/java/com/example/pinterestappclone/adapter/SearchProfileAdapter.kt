package com.example.pinterestappclone.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pinterestappclone.R
import com.example.pinterestappclone.model.Profile
import com.google.android.material.imageview.ShapeableImageView

class SearchProfileAdapter(var context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_VISIBLE = 0
        private const val VIEW_TYPE_GONE = 1
    }

    private var profiles = ArrayList<Profile>()

    @SuppressLint("NotifyDataSetChanged")
    fun addProfiles(profiles: ArrayList<Profile>) {
        this.profiles.addAll(profiles)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        val photosSize = profiles[position].photos.size
        return if (photosSize > 2) VIEW_TYPE_VISIBLE else VIEW_TYPE_GONE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_VISIBLE) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_search_profiles_visible, parent, false)
            return SearchProfileVisibleViewHolder(view)
        }

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_search_profiles_gone, parent, false)
        return SearchProfileGoneViewHolder(view)
    }

    class SearchProfileGoneViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivProfile: ImageView = view.findViewById(R.id.iv_profile)
        val tvFullName: TextView = view.findViewById(R.id.tv_fullName)
        val tvFollowers: TextView = view.findViewById(R.id.tv_followers)
    }

    class SearchProfileVisibleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivPhotoFirst: ImageView = view.findViewById(R.id.iv_photo_first)
        val ivPhotoSecond: ImageView = view.findViewById(R.id.iv_photo_second)
        val ivPhotoThird: ImageView = view.findViewById(R.id.iv_photo_third)
        val ivProfile: ImageView = view.findViewById(R.id.iv_profile)
        val tvFullName: TextView = view.findViewById(R.id.tv_fullName)
        val tvFollowers: TextView = view.findViewById(R.id.tv_followers)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val profile = profiles[position]
        if (holder is SearchProfileVisibleViewHolder) {
            val photos = profile.photos
            Glide.with(context).load(photos[0].urls.small).placeholder(ColorDrawable(Color.GRAY))
                .into(holder.ivPhotoFirst)
            Glide.with(context).load(photos[1].urls.small).placeholder(ColorDrawable(Color.GRAY))
                .into(holder.ivPhotoSecond)
            Glide.with(context).load(photos[2].urls.small).placeholder(ColorDrawable(Color.GRAY))
                .into(holder.ivPhotoThird)
            Glide.with(context).load(profile.profile_image.medium).placeholder(ColorDrawable(Color.GRAY))
                .into(holder.ivProfile)
            profile.last_name.let { holder.tvFullName.text = "${profile.first_name} $it" }
            holder.tvFollowers.text = "${profile.total_likes} followers"
        }

        if (holder is SearchProfileGoneViewHolder) {
            Glide.with(context).load(profile.profile_image.medium).placeholder(ColorDrawable(Color.GRAY))
                .into(holder.ivProfile)
            profile.last_name.let { holder.tvFullName.text = "${profile.first_name} $it" }
            holder.tvFollowers.text = "${profile.total_likes} followers"
        }
    }

    override fun getItemCount(): Int {
        return profiles.size
    }
}