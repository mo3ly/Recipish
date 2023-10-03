package com.example.food_recipes.core.datamodels

import com.google.gson.annotations.SerializedName

data class Recipes (
    @SerializedName("results")
    val recipes: List<Recipe>
)
