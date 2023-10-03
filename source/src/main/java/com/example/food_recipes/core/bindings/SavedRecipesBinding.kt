package com.example.food_recipes.core.bindings

import android.view.View
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.food_recipes.core.adapters.SavedRecipesAdapter
import com.example.food_recipes.core.data.local.database.entities.SavedRecipesEntity

class SavedRecipesBinding {
    companion object {

        @BindingAdapter("setVisibility", "setData", requireAll = false)
        @JvmStatic
        fun setVisibility(
            view: View,
            savedRecipes: List<SavedRecipesEntity>?,
            savedRecipesAdapter: SavedRecipesAdapter?
        ) {
            when (view) {
                is RecyclerView -> {
                    val data = savedRecipes.isNullOrEmpty()
                    view.isInvisible = data
                    if (!data) {
                        savedRecipes?.let { savedRecipesAdapter?.setSavedRecipes(it) }
                    }
                }
                else -> view.isVisible = savedRecipes.isNullOrEmpty()
            }
        }
    }
}