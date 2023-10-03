package com.example.food_recipes.core.adapters

import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.food_recipes.core.adapters.viewholders.RecommendedRecipesViewHolder
import com.example.food_recipes.core.datamodels.RandomRecipes
import com.example.food_recipes.core.datamodels.Recipe
import com.example.food_recipes.core.datamodels.Recipes
import com.example.food_recipes.misc.ViewDiffUtil
import com.example.food_recipes.ui.home.HomeFragmentDirections
import com.example.food_recipes.ui.saved.SavedFragmentDirections

class RecommendedRecipesAdapter : RecyclerView.Adapter<RecommendedRecipesViewHolder>() {
    private var recommendedRecipes = emptyList<Recipe>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecommendedRecipesViewHolder {
        return RecommendedRecipesViewHolder.from(parent)
    }

    override fun onBindViewHolder(
        holder: RecommendedRecipesViewHolder,
        position: Int
    ) {
        val currRecommendedRecipe = recommendedRecipes[position]
        holder.bind(currRecommendedRecipe)

        holder.binding.recommendedRecipeItemLayout.setOnClickListener {
            holder.itemView.findNavController().navigate(
                HomeFragmentDirections.actionNavigationHomeToRecipeDetailsActivity(
                    currRecommendedRecipe
                )
            )
        }
    }

    override fun getItemCount(): Int {
        return recommendedRecipes.size
    }

    fun setRecommendedRecipes(newRecommendedRecipesData: RandomRecipes) {
        val diffUtilResult = DiffUtil.calculateDiff(
            ViewDiffUtil(
                recommendedRecipes,
                newRecommendedRecipesData.recipes
            )
        )

        recommendedRecipes = newRecommendedRecipesData.recipes

        diffUtilResult.dispatchUpdatesTo(this)
    }
}