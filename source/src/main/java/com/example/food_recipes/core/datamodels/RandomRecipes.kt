package com.example.food_recipes.core.datamodels

import com.google.gson.annotations.SerializedName

data class RandomRecipes (
    @SerializedName("recipes")
    val recipes: List<Recipe>
)
