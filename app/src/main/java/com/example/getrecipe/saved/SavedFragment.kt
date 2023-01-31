package com.example.getrecipe.saved

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.getrecipe.databinding.FragmentSavedBinding
import com.example.getrecipe.viewModel.RecipesViewModel

class SavedFragment : Fragment() {
    private lateinit var binding: FragmentSavedBinding
    private lateinit var viewModel: RecipesViewModel

    // LATER ON CREATE ADAPTER & VIEWHOLDER

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(requireActivity())[RecipesViewModel::class.java]
        binding = FragmentSavedBinding.inflate(inflater, container, false)
        binding.savedRecyclerView.setHasFixedSize(true)
        binding.savedRecyclerView.layoutManager = LinearLayoutManager(context)
        // SET RECYCLER VIEW ITEM ETC.
        // here I should get all saved recipes from viewModel
        println(viewModel.getSavedRecipes.value) // prints null
        binding.info.text = viewModel.getSavedRecipes.value?.size.toString()

        viewModel.getSavedRecipes.observe(viewLifecycleOwner) { recipe ->
            println(recipe)
        }

        // Inflate the layout for this fragment
        return binding.root
    }
}