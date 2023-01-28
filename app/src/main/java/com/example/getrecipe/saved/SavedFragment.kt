package com.example.getrecipe.saved

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.getrecipe.R

class SavedFragment : Fragment() {

    // LATER ON CREATE ADAPTER & VIEWHOLDER

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // SET RECYCLER VIEW ITEM ETC.

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_saved, container, false)
    }
}