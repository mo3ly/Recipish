package com.example.food_recipes.core.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.food_recipes.core.data.local.database.entities.RecipesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipesDao {
    @Query("select * from recipes_tb order by id asc")
    fun readRecipes(): Flow<List<RecipesEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipesEntity: RecipesEntity)
}