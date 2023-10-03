package com.example.food_recipes.core.adapters.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.food_recipes.core.data.local.database.entities.SavedRecipesEntity
import com.example.food_recipes.databinding.LayoutSavedRecipeItemBinding

class SavedRecipesViewHolder(val binding: LayoutSavedRecipeItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(savedRecipe: SavedRecipesEntity) {
        binding.savedRecipes = savedRecipe
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): SavedRecipesViewHolder {
            return SavedRecipesViewHolder(
                LayoutSavedRecipeItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent,
                    false
                )
            )
        }
    }
}
