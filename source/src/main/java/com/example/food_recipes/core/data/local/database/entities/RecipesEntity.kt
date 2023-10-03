package com.example.food_recipes.core.data.local.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.food_recipes.core.datamodels.Recipes
import com.example.food_recipes.misc.Config.Companion.TB_RECIPES

@Entity(tableName = TB_RECIPES)
class RecipesEntity(var recipes: Recipes) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}