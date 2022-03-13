package com.example.pinterestappclone.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.text.Html
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
import com.example.pinterestappclone.model.Topic
import com.google.android.material.imageview.ShapeableImageView

class UpdatesAdapter(var context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var topics = ArrayList<Topic>()

    @SuppressLint("NotifyDataSetChanged")
    fun addTopics(topics: ArrayList<Topic>) {
        this.topics.addAll(topics)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_updates_element, parent, false)
        return UpdatesViewHolder(view)
    }

    class UpdatesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivProfile: ImageView = view.findViewById(R.id.iv_profile)
        val tvTitle: TextView = view.findViewById(R.id.tv_title)
        val tvDescription: TextView = view.findViewById(R.id.tv_description)
        val ivPhotoFirst: ImageView = view.findViewById(R.id.iv_photo_first)
        val ivPhotoSecond: ImageView = view.findViewById(R.id.iv_photo_second)
        val ivPhotoThird: ImageView = view.findViewById(R.id.iv_photo_third)
        val ivPhotoFourth: ImageView = view.findViewById(R.id.iv_photo_fourth)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val topic = topics[position]
        if (holder is UpdatesViewHolder) {
            val photos = topic.previewPhotos!!
            Glide.with(context).load(photos[0].urls!!.small).placeholder(ColorDrawable(Color.GRAY))
                .into(holder.ivPhotoFirst)
            Glide.with(context).load(photos[1].urls!!.small).placeholder(ColorDrawable(Color.GRAY))
                .into(holder.ivPhotoSecond)
            Glide.with(context).load(photos[2].urls!!.small).placeholder(ColorDrawable(Color.GRAY))
                .into(holder.ivPhotoThird)
            Glide.with(context).load(photos[3].urls!!.small).placeholder(ColorDrawable(Color.GRAY))
                .into(holder.ivPhotoFourth)

            Glide.with(context).load(topic.owners!![0].profile_image!!.medium)
                .placeholder(ColorDrawable(Color.GRAY))
                .into(holder.ivProfile)

            holder.tvTitle.text = topic.title

            holder.tvDescription.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(topic.description, Html.FROM_HTML_MODE_COMPACT)
            } else {
                Html.fromHtml(topic.description)
            }

        }
    }

    override fun getItemCount(): Int {
        return topics.size
    }
}