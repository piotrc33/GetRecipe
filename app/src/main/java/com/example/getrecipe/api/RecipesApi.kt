package com.example.getrecipe.api

import com.example.getrecipe.api_key
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipesApi {
    @GET("/recipes/random?apiKey=$api_key")
    suspend fun getRandomRecipe(@Query("tags") type: String): Root

}