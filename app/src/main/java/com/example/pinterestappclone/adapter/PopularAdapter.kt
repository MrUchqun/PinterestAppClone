package com.example.pinterestappclone.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pinterestappclone.R
import com.example.pinterestappclone.model.Topic

class PopularAdapter(private var context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var photoList = ArrayList<Topic>()

    @SuppressLint("NotifyDataSetChanged")
    fun addPhotos(photoList: ArrayList<Topic>) {
        this.photoList.clear()
        this.photoList.addAll(photoList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_popular_page, parent, false)
        return PopularViewHolder(view)
    }

    class PopularViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivPhoto: ImageView = view.findViewById(R.id.iv_photo)
        val tvTitle: TextView = view.findViewById(R.id.tv_title)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val topic = photoList[position]
        if (holder is PopularViewHolder) {
            Glide.with(context).load(topic.coverPhoto!!.urls!!.small)
                .placeholder(ColorDrawable(Color.parseColor(topic.coverPhoto.color)))
                .into(holder.ivPhoto)

            holder.tvTitle.text = topic.title
        }
    }

    override fun getItemCount(): Int {
        return photoList.size
    }
}