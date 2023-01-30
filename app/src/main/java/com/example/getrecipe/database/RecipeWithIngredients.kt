package com.example.getrecipe.database

import androidx.room.Embedded
import androidx.room.Relation

data class RecipeWithIngredients(
    @Embedded val recipeDB: RecipeDB,

    @Relation(
        parentColumn = "recipeId",
        entityColumn = "recipeId"
    )

    val ingredientDBS: List<IngredientDB>
)
