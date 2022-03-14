package com.example.pinterestappclone.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
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
import com.example.pinterestappclone.activity.DetailsActivity
import com.example.pinterestappclone.activity.MainActivity
import com.example.pinterestappclone.managers.PrefsManager
import com.example.pinterestappclone.model.PhotoItem
import com.example.pinterestappclone.model.PhotoList
import com.google.gson.Gson

class ResultPhotosAdapter(private var context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val prefsManager = PrefsManager.getInstance(context)
    private var photoList = ArrayList<PhotoItem>()

    @SuppressLint("NotifyDataSetChanged")
    fun addPhotos(photoList: ArrayList<PhotoItem>) {
        this.photoList.addAll(photoList)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addNewPhotos(photoList: ArrayList<PhotoItem>) {
        this.photoList.clear()
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

        if (holder is PhotoViewHolder) {
            holder.tvDescription.text = photoItem.user!!.bio
            Glide.with(context).load(photoUrl).placeholder(ColorDrawable(Color.parseColor(photoColor)))
                .into(holder.ivPhoto)

            holder.ivPhoto.setOnClickListener {
                callDetails(position)
            }
        }

    }

    private fun callDetails(position: Int) {
        val intent = Intent(context, DetailsActivity::class.java)
        intent.putExtra("position", position)
        prefsManager!!.saveArrayList(PrefsManager.KEY_PHOTO_LIST, photoList)
        context.startActivity(intent)
    }

    override fun getItemCount(): Int {
        return photoList.size
    }
}