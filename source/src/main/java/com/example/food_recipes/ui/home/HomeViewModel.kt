package com.example.food_recipes.ui.home

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.observe
import com.example.food_recipes.core.adapters.RecommendedRecipesAdapter
import com.example.food_recipes.misc.APIConstants
import com.example.food_recipes.misc.Config
import com.example.food_recipes.network.ResponseResult
import com.example.food_recipes.viewmodels.MainViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    application: Application
) :
    AndroidViewModel(application) {
    private fun setOptionsParams(): HashMap<String, String> {
        return hashMapOf(
            APIConstants.PARAM_API_KEY to Config.API_KEY,
            APIConstants.PARAM_NUMBER to APIConstants.DEFAULT_RANDOM_RESULTS_COUNT,
            APIConstants.PARAM_TAGS to ""
        )
    }

    fun randomRecipeRequest(
        mainViewModel: MainViewModel,
        recommendedRecipesAdapter: RecommendedRecipesAdapter,
        loaded: Boolean,
        homeFragment: HomeFragment
    ) {
        if (!loaded) {
            mainViewModel.getRecommendedRecipes(setOptionsParams())
            mainViewModel.recommendedRecipesResponse.observe(homeFragment.viewLifecycleOwner) { response ->
                when (response) {
                    is ResponseResult.Success -> {
                        response.result?.let { recommendedRecipesAdapter.setRecommendedRecipes(it) }
                    }
                    is ResponseResult.Error -> {
                        Toast.makeText(
                            homeFragment.context,
                            response.message.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is ResponseResult.Loading -> {
                    }
                }
            }
        }
        homeFragment.loaded = true
    }
}