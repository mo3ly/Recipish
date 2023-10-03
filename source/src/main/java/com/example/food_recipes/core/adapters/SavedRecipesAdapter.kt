package com.example.food_recipes.core.adapters

import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.food_recipes.core.adapters.viewholders.SavedRecipesViewHolder
import com.example.food_recipes.core.data.local.database.entities.SavedRecipesEntity
import com.example.food_recipes.misc.ViewDiffUtil
import com.example.food_recipes.ui.saved.SavedFragmentDirections

class SavedRecipesAdapter: RecyclerView.Adapter<SavedRecipesViewHolder>() {
    private var savedRecipes = emptyList<SavedRecipesEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedRecipesViewHolder {
        return SavedRecipesViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: SavedRecipesViewHolder, position: Int) {
        val currSavedRecipe = savedRecipes[position]
        holder.bind(currSavedRecipe)

        holder.binding.savedRecipeItemLayout.setOnClickListener {
            holder.itemView.findNavController().navigate(
                SavedFragmentDirections.actionNavigationSavedToRecipeDetailsActivity(
                    currSavedRecipe.recipe
                )
            )
        }
    }

    override fun getItemCount(): Int {
        return savedRecipes.size
    }

    fun setSavedRecipes(newSavedRecipes: List<SavedRecipesEntity>) {
        val diffUtilResult = DiffUtil.calculateDiff(
            ViewDiffUtil(
                savedRecipes,
                newSavedRecipes
            )
        )

        savedRecipes = newSavedRecipes

        diffUtilResult.dispatchUpdatesTo(this)
    }
}