package com.example.food_recipes.core.data.remote

import com.example.food_recipes.core.data.remote.api.SpoonacularAPI
import com.example.food_recipes.core.datamodels.RandomRecipes
import com.example.food_recipes.core.datamodels.Recipes
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val api: SpoonacularAPI
) {
    suspend fun getRecipes(params: Map<String, String>): Response<Recipes> {
        return api.getRecipes(params)
    }

    suspend fun findRecipes(params: Map<String, String>): Response<Recipes> {
        return api.findRecipes(params)
    }

    suspend fun getRecommendedRecipes(params: Map<String, String>): Response<RandomRecipes> {
        return api.getRecommendedRecipes(params)
    }
}