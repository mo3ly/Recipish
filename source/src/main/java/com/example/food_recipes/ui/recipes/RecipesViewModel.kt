package com.example.food_recipes.ui.recipes

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.lifecycle.viewModelScope
import com.example.food_recipes.core.adapters.RecipesAdapter
import com.example.food_recipes.core.repositories.RecipesDSRepository
import com.example.food_recipes.misc.APIConstants.Companion.DEFAULT_CUISINE
import com.example.food_recipes.misc.APIConstants.Companion.DEFAULT_DIET
import com.example.food_recipes.misc.APIConstants.Companion.DEFAULT_RESULTS_COUNT
import com.example.food_recipes.misc.APIConstants.Companion.DEFAULT_TYPE
import com.example.food_recipes.misc.APIConstants.Companion.PARAM_API_KEY
import com.example.food_recipes.misc.APIConstants.Companion.PARAM_CUISINE
import com.example.food_recipes.misc.APIConstants.Companion.PARAM_DIET
import com.example.food_recipes.misc.APIConstants.Companion.PARAM_NUMBER
import com.example.food_recipes.misc.APIConstants.Companion.PARAM_QUERY
import com.example.food_recipes.misc.APIConstants.Companion.PARAM_RECIPE_INFO
import com.example.food_recipes.misc.APIConstants.Companion.PARAM_RECIPE_INGREDIENTS
import com.example.food_recipes.misc.APIConstants.Companion.PARAM_TYPE
import com.example.food_recipes.misc.Config.Companion.API_KEY
import com.example.food_recipes.network.ResponseResult
import com.example.food_recipes.viewmodels.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(
    application: Application,
    private val recipesDSRepository: RecipesDSRepository
) :
    AndroidViewModel(application) {

    private var cuisine = DEFAULT_CUISINE
    private var mealType = DEFAULT_TYPE
    private var dietType = DEFAULT_DIET

    val savedRecipeOptions = recipesDSRepository.readRecipeOptions

    fun saveRecipeOptions(
        cuisine: String,
        cuisineId: Int,
        mealType: String,
        mealTypeId: Int,
        dietType: String,
        dietTypeId: Int
    ) =
        viewModelScope.launch(Dispatchers.IO) {
            recipesDSRepository.saveRecipeOptions(
                cuisine,
                cuisineId,
                mealType,
                mealTypeId,
                dietType,
                dietTypeId
            )
        }

    fun setOptionsParams(): HashMap<String, String> {
        viewModelScope.launch {
            savedRecipeOptions.collect { options ->
                cuisine = options.Cuisine
                mealType = options.MealType
                dietType = options.DietType
            }
        }

        return hashMapOf(
            PARAM_API_KEY to API_KEY,
            PARAM_NUMBER to DEFAULT_RESULTS_COUNT,
            PARAM_CUISINE to cuisine,
            PARAM_TYPE to mealType,
            PARAM_DIET to dietType,
            PARAM_RECIPE_INGREDIENTS to "true",
            PARAM_RECIPE_INFO to "true",
        )
    }

    fun setSearchParams(searchValue: String): HashMap<String, String> {
        return hashMapOf(
            PARAM_API_KEY to API_KEY,
            PARAM_QUERY to searchValue,
            PARAM_NUMBER to DEFAULT_RESULTS_COUNT,
            PARAM_RECIPE_INGREDIENTS to "true",
            PARAM_RECIPE_INFO to "true",
        )
    }


    fun recipesRequest(
        mainViewModel: MainViewModel,
        recipesAdapter: RecipesAdapter,
        recipesFragment: RecipesFragment
    ) {
        mainViewModel.getRecipes(setOptionsParams())
        mainViewModel.recipesResponse.observe(recipesFragment.viewLifecycleOwner) { response ->
            when (response) {
                is ResponseResult.Success -> {
                    recipesFragment.loadingEffect(false)
                    response.result?.let { recipesAdapter.setRecipes(it) }
                }
                is ResponseResult.Error -> {
                    recipesFragment.loadingEffect(false)
                    loadDataFromCache(mainViewModel, recipesAdapter, recipesFragment)
                    recipesFragment.showSnackBar(response.message.toString())
                }
                is ResponseResult.Loading -> {
                    recipesFragment.loadingEffect(true)
                }
            }
        }
    }

    fun searchRequest(
        query: String?,
        mainViewModel: MainViewModel,
        recipesAdapter: RecipesAdapter,
        recipesFragment: RecipesFragment
    ) {
        if (query != null) {
            recipesFragment.loadingEffect(true)
            mainViewModel.findRecipes(setSearchParams(query))
            mainViewModel.findRecipesResponse.observe(recipesFragment.viewLifecycleOwner) { response ->
                when (response) {
                    is ResponseResult.Success -> {
                        recipesFragment.loadingEffect(false)
                        response.result?.let { recipesAdapter.setRecipes(it) }
                    }
                    is ResponseResult.Error -> {
                        recipesFragment.loadingEffect(false)
                        recipesFragment.showSnackBar(response.message.toString())
                    }
                    is ResponseResult.Loading -> {
                        recipesFragment.loadingEffect(true)
                    }
                }
            }
        }
    }

    private fun loadDataFromCache(
        mainViewModel: MainViewModel,
        recipesAdapter: RecipesAdapter,
        recipesFragment: RecipesFragment
    ) {
        recipesFragment.lifecycleScope.launch {
            mainViewModel.readRecipes.observe(recipesFragment.viewLifecycleOwner) { db ->
                if (db.isNotEmpty()) {
                    recipesAdapter.setRecipes(db[0].recipes)
                }
            }
        }
    }
}