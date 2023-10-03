package com.example.food_recipes.core.data.remote.api

import com.example.food_recipes.core.datamodels.RandomRecipes
import com.example.food_recipes.core.datamodels.Recipes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface SpoonacularAPI {

    @GET("/recipes/complexSearch")
    suspend fun getRecipes(@QueryMap params: Map<String, String>): Response<Recipes>

    @GET("/recipes/complexSearch")
    suspend fun findRecipes(@QueryMap params: Map<String, String>): Response<Recipes>

    @GET("recipes/random")
    suspend fun getRecommendedRecipes(@QueryMap params: Map<String, String>): Response<RandomRecipes>
}