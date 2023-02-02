package com.example.getrecipe.saved

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.getrecipe.database.RecipeDB
import com.example.getrecipe.databinding.SavedRecipeItemBinding

class SavedAdapter(private val recipes: List<RecipeDB>) :
    RecyclerView.Adapter<SavedAdapter.SavedViewHolder>() {

    inner class SavedViewHolder(binding: SavedRecipeItemBinding): RecyclerView.ViewHolder(binding.root) {
        val recipeTitleTV = binding.savedRecipeTitle
        val savedRecipeCardView = binding.savedRecipeCardView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val countryItemBinding = SavedRecipeItemBinding.inflate(inflater, parent, false)
        return SavedViewHolder(countryItemBinding)
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    override fun onBindViewHolder(holder: SavedViewHolder, position: Int) {
        val current = recipes[position]
        holder.recipeTitleTV.text = current.title
        holder.savedRecipeCardView.setOnClickListener {
            val action = SavedFragmentDirections.actionSavedFragmentToDetailFragment(
                recipeId = current.recipeId
            )
            holder.savedRecipeCardView.findNavController().navigate(action)
        }
    }

}