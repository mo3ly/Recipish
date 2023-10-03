package com.example.food_recipes.core.data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.food_recipes.core.data.local.database.entities.SavedRecipesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedRecipesDao {
    @Query("select * from saved_recipes_tb order by id desc")
    fun readSavedRecipes(): Flow<List<SavedRecipesEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSavedRecipe(savedRecipesEntity: SavedRecipesEntity)

    @Delete
    suspend fun deleteSavedRecipe(savedRecipesEntity: SavedRecipesEntity)

    @Query("delete from saved_recipes_tb")
    suspend fun deleteAllSavedRecipes()
}