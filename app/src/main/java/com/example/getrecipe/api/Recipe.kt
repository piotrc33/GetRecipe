package com.example.getrecipe.api

import com.google.gson.annotations.SerializedName

data class Root(
    val recipes: List<Recipe>,
)

data class Recipe(
    val id: Int,
    val title: String,
    @SerializedName("extendedIngredients")
    val ingredients: List<Ingredient>,
    val instructions: String,
    val image: String
)

data class Ingredient(
    val name: String,
    val measures: Measures
)

data class Measures(
    val metric: Metric
)
data class Metric(
    val amount: Double,
    val unitShort: String,
    val unitLong: String
)
