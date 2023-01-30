package com.example.getrecipe.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [RecipeDB::class, IngredientDB::class], version = 1, exportSchema = false)
abstract class SavedRecipesDatabase : RoomDatabase() {
    abstract fun RecipesDao() : RecipesDao

    companion object {
        @Volatile
        private var INSTANCE: SavedRecipesDatabase? = null

        fun getDatabase(context: Context): SavedRecipesDatabase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SavedRecipesDatabase::class.java,
                    "recipe_database"
                ).build().also { INSTANCE = it }
                instance
            }
        }
    }
}