package com.example.food_recipes.core.data.local.database

import androidx.room.TypeConverter
import com.example.food_recipes.core.datamodels.Recipe
import com.example.food_recipes.core.datamodels.Recipes
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RecipesTypeConvertor {
    var gson = Gson()

    @TypeConverter
    fun recipesToString(recipes: Recipes): String {
        return gson.toJson(recipes)
    }

    @TypeConverter
    fun stringToRecipes(str: String): Recipes {
        return gson.fromJson(str, object : TypeToken<Recipes>() {}.type)
    }

    @TypeConverter
    fun recipeToString(recipe: Recipe): String {
        return gson.toJson(recipe)
    }

    @TypeConverter
    fun stringToRecipe(str: String): Recipe {
        return gson.fromJson(str, object : TypeToken<Recipe>() {}.type)
    }
}