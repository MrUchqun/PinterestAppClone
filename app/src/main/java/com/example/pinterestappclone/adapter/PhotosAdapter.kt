package com.example.pinterestappclone.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.media.Image
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pinterestappclone.R
import com.example.pinterestappclone.model.PhotoItem
import com.example.pinterestappclone.model.PhotoList
import com.squareup.picasso.Picasso
import java.util.logging.Handler

class PhotosAdapter(var context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var photoList = PhotoList()

    @SuppressLint("NotifyDataSetChanged")
    fun addPhotos(photoList: PhotoList) {
        this.photoList.addAll(photoList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_photos_view, parent, false)
        return PhotoViewHolder(view)
    }

    class PhotoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivPhoto: ImageView = view.findViewById(R.id.iv_photo)
        val tvDescription: TextView = view.findViewById(R.id.tv_description)
        val ivMore: ImageView = view.findViewById(R.id.iv_btn_more)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val photoItem = photoList[position]
        val photoColor = photoItem.color
        val photoUrl = photoItem.urls!!.thumb
        val s1 = photoItem.alt_description
        val s2 = photoItem.description
        val s3 = photoItem.user!!.name

        if (holder is PhotoViewHolder) {
            holder.tvDescription.text = getDescription(s1, s2, s3)
            Picasso.get().load(photoUrl).placeholder(ColorDrawable(Color.parseColor(photoColor)))
                .into(holder.ivPhoto)
        }

    }

    private fun getDescription(s1: Any?, s2: String?, s3: String?): String {
        return when {
            s1 != null -> s1.toString()
            s2 != null -> s2.toString()
            else -> "Photo was made by $s3"
        }
    }

    override fun getItemCount(): Int {
        return photoList.size
    }
}