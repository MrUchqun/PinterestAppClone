package com.example.pinterestappclone.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pinterestappclone.R

class MessagesFragment : Fragment() {

    companion object {
        fun newInstance(): MessagesFragment {
            return MessagesFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_massages_page, container, false)
    }
}