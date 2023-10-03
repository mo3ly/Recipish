package com.example.food_recipes.modules

import android.content.Context
import androidx.room.Room
import com.example.food_recipes.core.data.local.database.FoodRecipesDatabase
import com.example.food_recipes.misc.Config.Companion.DB_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, FoodRecipesDatabase::class.java, DB_NAME).build()

    @Singleton
    @Provides
    fun provideRecipesDao(foodRecipesDatabase: FoodRecipesDatabase) = foodRecipesDatabase.recipesDao()


    @Singleton
    @Provides
    fun provideSavedRecipesDao(foodRecipesDatabase: FoodRecipesDatabase) = foodRecipesDatabase.savedRecipesDao()
}