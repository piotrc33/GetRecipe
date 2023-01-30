package com.example.getrecipe.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_recipes")
data class RecipeDB(
    @PrimaryKey
    val recipeId: Int,
    val title: String,
    val instructions: String,
    val image: String
)