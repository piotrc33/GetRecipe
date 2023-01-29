package com.example.getrecipe.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ingredients")
data class Ingredient(
    // generating ids automatically because same ingredient with same id from api can be used multiple times to different recipes
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val name: String,
    val measure: String,
    val recipeId: Int
)
