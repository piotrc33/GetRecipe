package com.example.getrecipe.savedDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.getrecipe.R
import com.example.getrecipe.databinding.FragmentDetailBinding
import com.example.getrecipe.formIngredientsDBString
import com.example.getrecipe.removeTagsFromString
import com.example.getrecipe.viewModel.RecipesViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DetailFragment : Fragment() {
    private lateinit var recipeId : Number
    private lateinit var binding: FragmentDetailBinding
    private lateinit var viewModel: RecipesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let { recipeId = it.getInt("recipeId") }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(requireActivity())[RecipesViewModel::class.java]
        binding = FragmentDetailBinding.inflate(inflater, container, false)

        lifecycleScope.launch {
            val recipeWithIngredients = viewModel.db.RecipesDao().getRecipeWithIngredients(recipeId.toInt())
            // TITLE
            binding.recipeTitle.text = recipeWithIngredients.recipe.title

            // IMAGE
            Picasso.get().load(recipeWithIngredients.recipe.image).into(binding.recipeImage)

            // INGREDIENTS
            binding.recipeIngredients.text = formIngredientsDBString(recipeWithIngredients.ingredients)

            // INSTRUCTIONS
            var instructionsString = "INSTRUCTIONS: \n\n"
            instructionsString += removeTagsFromString(recipeWithIngredients.recipe.instructions)
            binding.recipeInstructions.text = instructionsString
        }

        return binding.root
    }
}