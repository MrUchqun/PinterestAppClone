package com.example.pinterestappclone.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pinterestappclone.R
import com.example.pinterestappclone.model.PhotoList
import com.squareup.picasso.Picasso

class IdeaPageAdapter(var context: Context, var list: PhotoList) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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
            Picasso.get().load(photo.urls!!.thumb)
                .placeholder(ColorDrawable(Color.parseColor(photo.color))).into(holder.ivPhoto)

            Picasso.get().load(photo.user!!.profile_image!!.medium)
                .placeholder(ColorDrawable(Color.parseColor(photo.color))).into(holder.ivProfile)

            val count = photo.likes!!
            holder.tvCount.setText(if (count > 9) count % 10 else count)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}