package com.example.food_recipes.core.repositories

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.example.food_recipes.misc.APIConstants.Companion.DEFAULT_CUISINE
import com.example.food_recipes.misc.APIConstants.Companion.DEFAULT_DIET
import com.example.food_recipes.misc.APIConstants.Companion.DEFAULT_TYPE
import com.example.food_recipes.misc.APIConstants.Companion.PREFERENCES_CUISINE
import com.example.food_recipes.misc.APIConstants.Companion.PREFERENCES_CUISINE_ID
import com.example.food_recipes.misc.APIConstants.Companion.PREFERENCES_DIET
import com.example.food_recipes.misc.APIConstants.Companion.PREFERENCES_DIET_ID
import com.example.food_recipes.misc.APIConstants.Companion.PREFERENCES_DS_NAME
import com.example.food_recipes.misc.APIConstants.Companion.PREFERENCES_TYPE
import com.example.food_recipes.misc.APIConstants.Companion.PREFERENCES_TYPE_ID
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private val Context.recipeOptionsDataStore by preferencesDataStore(PREFERENCES_DS_NAME)

@ActivityRetainedScoped
class RecipesDSRepository @Inject constructor(@ApplicationContext private val context: Context) {

    private val recipeOptionsDataStore: DataStore<Preferences> = context.recipeOptionsDataStore

    private object OptionsKeys {
        val Cuisine = stringPreferencesKey(PREFERENCES_CUISINE)
        val CuisineId = intPreferencesKey(PREFERENCES_CUISINE_ID)

        val MealType = stringPreferencesKey(PREFERENCES_TYPE)
        val MealTypeId = intPreferencesKey(PREFERENCES_TYPE_ID)

        val DietType = stringPreferencesKey(PREFERENCES_DIET)
        val DietTypeId = intPreferencesKey(PREFERENCES_DIET_ID)
    }

    suspend fun saveRecipeOptions(
        cuisine: String,
        cuisineId: Int,
        mealType: String,
        mealTypeId: Int,
        dietType: String,
        dietTypeId: Int
    ) {
        recipeOptionsDataStore.edit { pref ->
            pref[OptionsKeys.Cuisine] = cuisine
            pref[OptionsKeys.CuisineId] = cuisineId
            pref[OptionsKeys.MealType] = mealType
            pref[OptionsKeys.MealTypeId] = mealTypeId
            pref[OptionsKeys.DietType] = dietType
            pref[OptionsKeys.DietTypeId] = dietTypeId
        }
    }

    val readRecipeOptions: Flow<RecipeOptions> = recipeOptionsDataStore.data.catch { e ->
        if (e is IOException) {
            emit(emptyPreferences())
        } else {
            throw e
        }
    }.map { pref ->
        RecipeOptions(
            pref[OptionsKeys.Cuisine] ?: DEFAULT_CUISINE,
            pref[OptionsKeys.CuisineId] ?: 0,
            pref[OptionsKeys.MealType] ?: DEFAULT_TYPE,
            pref[OptionsKeys.MealTypeId] ?: 0,
            pref[OptionsKeys.DietType] ?: DEFAULT_DIET,
            pref[OptionsKeys.DietTypeId] ?: 0
        )
    }
}
