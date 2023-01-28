package com.example.getrecipe.newRecipe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.getrecipe.R

class NewFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // SET ITEMS FROM API TO LAYOUT VIEWS

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new, container, false)
    }

}