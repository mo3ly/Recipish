package com.example.food_recipes.core.data.local

import com.example.food_recipes.core.data.local.database.dao.RecipesDao
import com.example.food_recipes.core.data.local.database.dao.SavedRecipesDao
import com.example.food_recipes.core.data.local.database.entities.RecipesEntity
import com.example.food_recipes.core.data.local.database.entities.SavedRecipesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val recipesDao: RecipesDao,
    private val savedRecipesDao: SavedRecipesDao
){
    fun readRecipes(): Flow<List<RecipesEntity>> {
        return recipesDao.readRecipes()
    }

    suspend fun insertRecipes(recipesEntity: RecipesEntity) {
        recipesDao.insertRecipes(recipesEntity)
    }

    fun readSavedRecipes(): Flow<List<SavedRecipesEntity>> {
        return savedRecipesDao.readSavedRecipes()
    }

    suspend fun insertSavedRecipe(savedRecipeEntity: SavedRecipesEntity) {
        savedRecipesDao.insertSavedRecipe(savedRecipeEntity)
    }

    suspend fun deleteSavedRecipe(savedRecipeEntity: SavedRecipesEntity){
        savedRecipesDao.deleteSavedRecipe(savedRecipeEntity)
    }

    suspend fun deleteAllSavedRecipes(){
        savedRecipesDao.deleteAllSavedRecipes()
    }
}