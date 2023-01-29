package com.example.getrecipe.api

data class Root(
    val recipes: List<Recipe>,
)

data class Recipe(
    val id: Number,
    val title: String,
    val extendedIngredients: List<Ingredient>,
    val instructions: String,
    val image: String
)

data class Ingredient(
//    val id: Int,
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
