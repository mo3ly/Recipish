package com.example.food_recipes.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.*
import com.example.food_recipes.core.data.local.database.entities.RecipesEntity
import com.example.food_recipes.core.data.local.database.entities.SavedRecipesEntity
import com.example.food_recipes.core.datamodels.RandomRecipes
import com.example.food_recipes.core.datamodels.Recipes
import com.example.food_recipes.network.ResponseResult
import com.example.food_recipes.core.repositories.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    var recipesResponse: MutableLiveData<ResponseResult<Recipes>> = MutableLiveData()
    var findRecipesResponse: MutableLiveData<ResponseResult<Recipes>> = MutableLiveData()
    var recommendedRecipesResponse: MutableLiveData<ResponseResult<RandomRecipes>> =
        MutableLiveData()

    val readRecipes: LiveData<List<RecipesEntity>> = repository.local.readRecipes().asLiveData()
    val readSavedRecipes: LiveData<List<SavedRecipesEntity>> =
        repository.local.readSavedRecipes().asLiveData()

    private fun insertRecipes(recipesEntity: RecipesEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertRecipes(recipesEntity)
        }

    fun insertSavedRecipe(savedRecipeEntity: SavedRecipesEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertSavedRecipe(savedRecipeEntity)
        }

    fun deleteSavedRecipe(savedRecipeEntity: SavedRecipesEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.deleteSavedRecipe(savedRecipeEntity)
        }

    fun deleteAllSavedRecipes() =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.deleteAllSavedRecipes()
        }


    fun getRecipes(params: Map<String, String>) = viewModelScope.launch {
        recipesResponse.value = ResponseResult.Loading()

        if (!isNetworkAvailable()) {
            recipesResponse.value = ResponseResult.Error("No Internet Connection.")
            return@launch
        }

        try {
            recipesResponse.value = handleAPIResponse(repository.remote.getRecipes(params))

            val recipes = recipesResponse.value!!.result
            if (recipes != null)
                cacheRecipes(recipes)

        } catch (_: Exception) {
            recipesResponse.value = ResponseResult.Error("Recipes not found.")
        }
    }

    fun findRecipes(params: Map<String, String>) = viewModelScope.launch {
        findRecipesResponse.value = ResponseResult.Loading()

        if (!isNetworkAvailable()) {
            recipesResponse.value = ResponseResult.Error("No Internet Connection.")
            return@launch
        }

        try {
            findRecipesResponse.value = handleAPIResponse(repository.remote.findRecipes(params))
        } catch (_: java.lang.Exception) {
            findRecipesResponse.value = ResponseResult.Error("Recipes not found.")
        }
    }

    fun getRecommendedRecipes(params: Map<String, String>) = viewModelScope.launch {
        recommendedRecipesResponse.value = ResponseResult.Loading()

        if (!isNetworkAvailable()) {
            recipesResponse.value = ResponseResult.Error("No Internet Connection.")
            return@launch
        }

        try {
            recommendedRecipesResponse.value =
                handleRandomAPIResponse(repository.remote.getRecommendedRecipes(params))
        } catch (_: Exception) {
            recommendedRecipesResponse.value =
                ResponseResult.Error("Recommended Recipes not found.")
        }
    }

    private fun cacheRecipes(recipes: Recipes) {
        val recipesEntity = RecipesEntity(recipes)
        insertRecipes(recipesEntity)
    }

    private fun handleRandomAPIResponse(response: Response<RandomRecipes>): ResponseResult<RandomRecipes> {
        return when {
            response.message().toString().contains("timeout") ->
                ResponseResult.Error("API request timeout")

            response.code() == 402 ->
                ResponseResult.Error("API key has been reached its limit.")

            response.body()!!.recipes.isEmpty() ->
                ResponseResult.Error("API Data not found.")

            response.isSuccessful ->
                ResponseResult.Success(response.body()!!)

            else ->
                ResponseResult.Error(response.message())
        }
    }

    private fun handleAPIResponse(response: Response<Recipes>): ResponseResult<Recipes> {
        return when {
            response.message().toString().contains("timeout") ->
                ResponseResult.Error("API request timeout")

            response.code() == 402 ->
                ResponseResult.Error("API key has been reached its limit.")

            response.body()!!.recipes.isEmpty() ->
                ResponseResult.Error("API Data not found.")

            response.isSuccessful ->
                ResponseResult.Success(response.body()!!)

            else ->
                ResponseResult.Error(response.message())
        }
    }

    /**
     * @return true when internet connection is available
     */
    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getApplication<Application>()
            .getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            else -> false
        }
    }
}