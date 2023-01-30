package com.example.getrecipe.newRecipe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.lifecycle.ViewModelProvider
import com.example.getrecipe.api.Ingredient
import com.example.getrecipe.api.Recipe
import com.example.getrecipe.databinding.FragmentNewBinding
import com.example.getrecipe.removeTagsFromString
import com.example.getrecipe.viewModel.RecipesViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewFragment : Fragment() {

    private lateinit var viewModel : RecipesViewModel
    private lateinit var binding: FragmentNewBinding
    val recipeTypes: List<String> = listOf("breakfast", "lunch", "dinner")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewBinding.inflate(inflater, container, false)
        binding.spinner.setSelection(0)

        // SET ITEMS FROM API TO LAYOUT VIEWS
        viewModel = ViewModelProvider(requireActivity())[RecipesViewModel::class.java]
        viewModel.recipe.observe(viewLifecycleOwner) { recipe ->
            showRecipe(recipe)
        }

        binding.nextButton.setOnClickListener {
            println(viewModel.currentRecipeIndex)
            if(viewModel.currentRecipeIndex == viewModel.recipesInSession.size - 1) {
                CoroutineScope(Dispatchers.Main).launch {
                    viewModel.loadRecipe()
                }
            }
            else{
                viewModel.currentRecipeIndex++
                viewModel.recipe.value = viewModel.recipesInSession[viewModel.currentRecipeIndex]
            }

        }

        binding.backButton.setOnClickListener {
            if(viewModel.currentRecipeIndex != 0){
                viewModel.currentRecipeIndex--
                viewModel.recipe.value = viewModel.recipesInSession[viewModel.currentRecipeIndex]
            }
        }

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.recipeType = recipeTypes[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                viewModel.recipeType = recipeTypes[0]
            }
        }

        binding.saveButton.setOnClickListener {
            if(viewModel.recipe.value != null) {
                viewModel.insertRecipe(viewModel.convertRecipeToDB(viewModel.recipe.value!!))
                viewModel.insertAllIngredients(viewModel.convertIngredientsToDB(viewModel.recipe.value!!.ingredients))
            }
        }

        return binding.root
    }

    private fun showRecipe(recipe: Recipe) {
        binding.recipeTitle.text = recipe.title
        // LOADING IMAGE
        Picasso.get().load(recipe.image).into(binding.recipeImage)

        // INGREDIENTS
        var ingredientString: String = "INGREDIENTS: \n\n"
        val ingredients: List<Ingredient> = recipe.ingredients
        ingredients.forEach { ingredient ->
            ingredientString += ingredient.name + " "
            ingredientString += ingredient.measures.metric.amount.toString() + " "
            ingredientString += ingredient.measures.metric.unitShort
            ingredientString += "\n"

        }

        // INSTRUCTIONS
//            println(recipe.instructions)
        var instructionsString = "INSTRUCTIONS: \n\n"
        instructionsString += removeTagsFromString(recipe.instructions)
        binding.recipeInstructions.text = instructionsString

        binding.recipeIngredients.text = ingredientString
    }



}