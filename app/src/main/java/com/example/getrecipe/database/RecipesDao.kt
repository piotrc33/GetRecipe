package com.example.getrecipe.database

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.OnConflictStrategy.Companion.REPLACE

@Dao
interface RecipesDao {
    @Insert(onConflict = IGNORE)
    suspend fun insertIngredient(ingredient: Ingredient)

    @Query("SELECT * FROM saved_recipes")
    fun getSavedRecipes(): LiveData<List<Recipe>>

    @Transaction
    @Query("SELECT * FROM saved_recipes WHERE recipeId = :recipeId")
    suspend fun getRecipeWithIngredients(recipeId: String): List<RecipeWithIngredients>


//    @Insert(onConflict = REPLACE)
//    suspend fun insert(recipe: Recipe)
//
//    @Delete
//    suspend fun delete(recipe: Recipe)
//
//    @Query("SELECT * FROM saved_recipes")
//    fun getAllSavedRecipes() : LiveData<List<Recipe>>
}