package com.example.getrecipe.database

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.Companion.IGNORE

@Dao
interface RecipesDao {
    @Insert(onConflict = IGNORE)
    suspend fun insertIngredient(ingredientDB: IngredientDB)

    @Insert(onConflict = IGNORE)
    suspend fun insertAllIngredients(ingredientsDB: List<IngredientDB>)

    @Insert(onConflict = IGNORE)
    suspend fun insertRecipe(recipeDB: RecipeDB)

    @Update
    suspend fun updateRecipe(recipe: RecipeDB)

    @Query("SELECT * FROM saved_recipes")
    fun getSavedRecipes(): LiveData<List<RecipeDB>>

//    @Transaction
//    @Query("SELECT * FROM saved_recipes WHERE recipeId = :recipeId")
//    suspend fun getRecipeWithIngredients(recipeId: String): List<RecipeWithIngredients>


//    @Insert(onConflict = REPLACE)
//    suspend fun insert(recipe: Recipe)
//
//    @Delete
//    suspend fun delete(recipe: Recipe)
//
//    @Query("SELECT * FROM saved_recipes")
//    fun getAllSavedRecipes() : LiveData<List<Recipe>>
}