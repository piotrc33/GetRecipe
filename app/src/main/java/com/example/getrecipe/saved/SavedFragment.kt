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

        viewModel.getSavedRecipes.observe(viewLifecycleOwner) { recipes ->
            val adapter = SavedAdapter(recipes)
            binding.savedRecyclerView.layoutManager = LinearLayoutManager(context)
            binding.savedRecyclerView.adapter = adapter
        }

        return binding.root
    }
}