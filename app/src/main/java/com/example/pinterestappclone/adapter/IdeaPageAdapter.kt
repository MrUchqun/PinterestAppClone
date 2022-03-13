package com.example.pinterestappclone.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pinterestappclone.R
import com.example.pinterestappclone.model.PhotoList

class IdeaPageAdapter(var context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var list = PhotoList()

    @SuppressLint("NotifyDataSetChanged")
    fun addList(list: PhotoList) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_ideas_page, parent, false)
        return IdeasViewHolder(view)
    }

    class IdeasViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivPhoto: ImageView = view.findViewById(R.id.iv_photo)
        val ivProfile: ImageView = view.findViewById(R.id.iv_profile)
        val tvCount: TextView = view.findViewById(R.id.tv_count)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val photo = list[position]
        if (holder is IdeasViewHolder) {
            Glide.with(context).load(photo.urls!!.thumb)
                .placeholder(ColorDrawable(Color.parseColor(photo.color))).into(holder.ivPhoto)

            Glide.with(context).load(photo.user!!.profile_image!!.medium)
                .placeholder(ColorDrawable(Color.parseColor(photo.color))).into(holder.ivProfile)

            val count = photo.likes!!.toInt()
            holder.tvCount.text = ((if (count > 9) count % 10 else count)).toString()
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}