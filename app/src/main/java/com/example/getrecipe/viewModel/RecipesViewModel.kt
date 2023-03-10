package com.example.getrecipe.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.example.getrecipe.api.Ingredient
import com.example.getrecipe.api.Recipe
import com.example.getrecipe.api.RecipesApi
import com.example.getrecipe.base_url
import com.example.getrecipe.database.IngredientDB
import com.example.getrecipe.database.RecipeDB
import com.example.getrecipe.database.RecipeWithIngredients
import com.example.getrecipe.database.SavedRecipesDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RecipesViewModel(application: Application): AndroidViewModel(application) {
    val db: SavedRecipesDatabase
    var recipe = MutableLiveData<Recipe>()
    var recipeType: String = "breakfast"

    var recipesInSession: List<Recipe> = listOf()
    var currentRecipeIndex = 0 // variable for index in current session

    val getSavedRecipes: LiveData<List<RecipeDB>>

    init {
        db = SavedRecipesDatabase.getDatabase(application)
        getSavedRecipes = db.RecipesDao().getSavedRecipes()
        viewModelScope.launch {
            loadRecipe()
        }
    }

    fun insertRecipe(recipeDB: RecipeDB) {
        viewModelScope.launch {
            db.RecipesDao().insertRecipe(recipeDB)
        }
    }
    fun deleteRecipeWithIngredients(recipeId: Int) {
        viewModelScope.launch {
            db.RecipesDao().deleteRecipe(recipeId)
            db.RecipesDao().deleteIngredients(recipeId)
        }
    }

    fun insertAllIngredients(ingredientsDB: List<IngredientDB>) {
        viewModelScope.launch {
            db.RecipesDao().insertAllIngredients(ingredientsDB)
        }
    }

    fun convertRecipeToDB(recipe: Recipe): RecipeDB{
        return RecipeDB(
            recipe.id,
            recipe.title,
            recipe.instructions,
            recipe.image
        )
    }
    fun convertIngredientsToDB(ingredients: List<Ingredient>): List<IngredientDB>{
        var ingredientsDB: List<IngredientDB> = mutableListOf()
        ingredients.forEach { i ->
            // id can be anything, later generated automatically
            val measure: String = i.measures.metric.amount.toString() + " " + i.measures.metric.unitShort
            ingredientsDB += IngredientDB(0, i.name, measure, recipe.value!!.id)
        }
        return ingredientsDB
    }

    suspend fun loadRecipe() {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(base_url)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        val api = retrofit.create(RecipesApi::class.java)

        viewModelScope.launch {
            recipe.value = getData(api, recipeType)
        }
    }

    private suspend fun getData(api: RecipesApi, type: String): Recipe {
        return withContext(Dispatchers.IO) {
            val response = async {api.getRandomRecipe(type = type)}
            val result = response.await()
            recipesInSession = recipesInSession + result.recipes[0]
            currentRecipeIndex = recipesInSession.size - 1
            return@withContext result.recipes[0]
        }
    }
}