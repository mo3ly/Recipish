package com.example.food_recipes.misc

class APIConstants {
    companion object{
        const val PARAM_API_KEY = "apiKey"
        const val PARAM_QUERY = "query"
        const val PARAM_NUMBER = "number"
        const val PARAM_TAGS = "tags"
        const val PARAM_CUISINE = "cuisine"
        const val PARAM_TYPE = "type"
        const val PARAM_DIET = "diet"
        const val PARAM_RECIPE_INGREDIENTS = "fillIngredients"
        const val PARAM_RECIPE_INFO = "addRecipeInformation"

        const val DEFAULT_RESULTS_COUNT = "25"
        const val DEFAULT_RANDOM_RESULTS_COUNT = "10"
        const val DEFAULT_CUISINE = "Middle Eastern"
        const val DEFAULT_TYPE = "Main Course"
        const val DEFAULT_DIET = "diet"

        const val PREFERENCES_DS_NAME = "Cuisine"
        const val PREFERENCES_CUISINE = "Cuisine"
        const val PREFERENCES_CUISINE_ID = "CuisineId"
        const val PREFERENCES_TYPE = "MealType"
        const val PREFERENCES_TYPE_ID = "MealTypeId"
        const val PREFERENCES_DIET = "DietType"
        const val PREFERENCES_DIET_ID = "DietTypeId"

        const val RECIPE_DETAILS_BUNDLE = "recipeDetailsBundle"
    }
}