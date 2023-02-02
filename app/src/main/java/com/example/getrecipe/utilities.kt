package com.example.getrecipe

import com.example.getrecipe.api.Ingredient
import com.example.getrecipe.database.IngredientDB

fun removeTagsFromString(string: String): String {
    return string.replace(Regex("<[^>]*>"), "")
}

fun formIngredientsDBString(ingredients: List<IngredientDB>): String {
    var ingredientString: String = "INGREDIENTS: \n\n"
    ingredients.forEach { ingredient ->
        ingredientString += ingredient.name + " "
        ingredientString += ingredient.measure+ " \n"

    }
    return ingredientString
}

fun formIngredientsString(ingredients: List<Ingredient>) : String {
    var ingredientString: String = "INGREDIENTS: \n\n"
    ingredients.forEach { ingredient ->
        ingredientString += ingredient.name + " "
        ingredientString += ingredient.measures.metric.amount.toString() + " "
        ingredientString += ingredient.measures.metric.unitShort
        ingredientString += "\n"

    }
    return ingredientString
}