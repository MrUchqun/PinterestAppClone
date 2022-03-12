package com.example.pinterestappclone.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
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
import com.example.pinterestappclone.activity.DetailsActivity
import com.example.pinterestappclone.activity.MainActivity
import com.example.pinterestappclone.managers.PrefsManager
import com.example.pinterestappclone.model.PhotoItem
import com.example.pinterestappclone.model.PhotoList
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import java.util.logging.Handler

class HelperTextAdapter(context: Context, var helperList: ArrayList<String>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val prefsManager = PrefsManager.getInstance(context)

    fun addHelper(text: String) {
        if (helperList.contains(text)) helperList.remove(text)
        val newList = ArrayList<String>()
        newList.add(text)
        newList.addAll(helperList)
        helperList.clear()
        helperList.addAll(newList)
        prefsManager!!.saveArrayList(PrefsManager.KEY_LIST, helperList)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearHelper(element: String) {
        helperList.remove(element)
        prefsManager!!.saveArrayList(PrefsManager.KEY_LIST, helperList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_search_helper_text, parent, false)
        return HelperTextViewHolder(view)
    }

    class HelperTextViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvHelp: TextView = view.findViewById(R.id.tv_search_helper)
        val ivClear: ImageView = view.findViewById(R.id.iv_clear)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val text = helperList[position]
        if (holder is HelperTextViewHolder) {
            holder.tvHelp.text = text

            holder.ivClear.setOnClickListener {
                clearHelper(text)
            }
        }
    }

    override fun getItemCount(): Int {
        return helperList.size
    }
}