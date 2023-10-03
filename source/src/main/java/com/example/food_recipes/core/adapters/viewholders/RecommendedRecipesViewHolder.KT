package com.example.food_recipes.core.adapters.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.food_recipes.core.datamodels.Recipe
import com.example.food_recipes.databinding.LayoutRecommendedRecipeItemBinding

class RecommendedRecipesViewHolder(val binding: LayoutRecommendedRecipeItemBinding):
    RecyclerView.ViewHolder(binding.root) {

    fun bind(recommendedRecipe: Recipe) {
        binding.recommendedRecipe = recommendedRecipe
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): RecommendedRecipesViewHolder {
            return RecommendedRecipesViewHolder(
                LayoutRecommendedRecipeItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent,
                    false
                )
            )
        }
    }
}
