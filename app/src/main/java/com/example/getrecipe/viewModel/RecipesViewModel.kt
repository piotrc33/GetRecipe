package com.example.getrecipe.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.example.getrecipe.api.Recipe
import com.example.getrecipe.api.RecipesApi
import com.example.getrecipe.base_url
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
    private val db: SavedRecipesDatabase
    var recipe = MutableLiveData<Recipe>()
    var currentRecipeIndex = 0
    var recipesInSession: List<Recipe> = listOf()
    val getAllSavedRecipes: LiveData<List<com.example.getrecipe.database.Recipe>>

    init {
        db = SavedRecipesDatabase.getDatabase(application)
        getAllSavedRecipes = db.RecipesDao().getSavedRecipes()
        viewModelScope.launch {
            loadRecipe()
        }
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
            recipe.value = getData(api, "breakfast")
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