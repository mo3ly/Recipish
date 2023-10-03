package com.example.food_recipes.core.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.food_recipes.core.adapters.viewholders.RecipesViewHolder
import com.example.food_recipes.core.datamodels.Recipe
import com.example.food_recipes.core.datamodels.Recipes
import com.example.food_recipes.misc.ViewDiffUtil

class RecipesAdapter : RecyclerView.Adapter<RecipesViewHolder>() {

    private var recipes = emptyList<Recipe>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesViewHolder {
        return RecipesViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecipesViewHolder, position: Int) {
        val currRecipe = recipes[position]
        holder.bind(currRecipe)
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    fun setRecipes(newRecipesData: Recipes) {
        // instead of notifyDataSetChanged() because it consumes resources on updating the view
        val diffUtilResult = DiffUtil.calculateDiff(
            ViewDiffUtil(
                recipes,
                newRecipesData.recipes
            )
        )

        recipes = newRecipesData.recipes

        diffUtilResult.dispatchUpdatesTo(this)
    }
}