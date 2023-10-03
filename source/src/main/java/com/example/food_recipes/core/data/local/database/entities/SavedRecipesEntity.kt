package com.example.food_recipes.core.data.local.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.food_recipes.core.datamodels.Recipe
import com.example.food_recipes.misc.Config.Companion.TB_SAVED_RECIPES

@Entity(tableName = TB_SAVED_RECIPES)
class SavedRecipesEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var recipe: Recipe,
)