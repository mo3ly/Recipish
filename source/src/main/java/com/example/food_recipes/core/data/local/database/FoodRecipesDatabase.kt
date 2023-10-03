package com.example.food_recipes.core.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.food_recipes.core.data.local.database.dao.RecipesDao
import com.example.food_recipes.core.data.local.database.dao.SavedRecipesDao
import com.example.food_recipes.core.data.local.database.entities.RecipesEntity
import com.example.food_recipes.core.data.local.database.entities.SavedRecipesEntity

@Database(
    entities = [
        RecipesEntity::class,
        SavedRecipesEntity::class
    ],
    version = 1,
    exportSchema = false
)

@TypeConverters(RecipesTypeConvertor::class)
abstract class FoodRecipesDatabase : RoomDatabase() {
    abstract fun recipesDao(): RecipesDao
    abstract fun savedRecipesDao(): SavedRecipesDao
}