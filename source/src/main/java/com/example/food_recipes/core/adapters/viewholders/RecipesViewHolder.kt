package com.example.food_recipes.core.adapters.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.food_recipes.core.datamodels.Recipe
import com.example.food_recipes.databinding.LayoutRecipeItemBinding

class RecipesViewHolder(private val binding: LayoutRecipeItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(recipe: Recipe) {
        binding.recipe = recipe
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): RecipesViewHolder {
            return RecipesViewHolder(
                LayoutRecipeItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }
}