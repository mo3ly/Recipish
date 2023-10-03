package com.example.food_recipes.misc

class Config {
    companion object{
        const val API_URL = "https://api.spoonacular.com/"
        const val API_IMAGE_URL = "https://spoonacular.com/cdn/ingredients_100x100/"
        const val API_KEY = "791469e243c14530a3b117d54a8b9f08"//30780a076480488f9c85c627c99a9b43 105973f690f84a75a748317d60b62bc3 791469e243c14530a3b117d54a8b9f08

        const val DB_NAME = "food_recipes_db"
        const val TB_RECIPES = "recipes_tb"
        const val TB_SAVED_RECIPES = "saved_recipes_tb"
    }
}