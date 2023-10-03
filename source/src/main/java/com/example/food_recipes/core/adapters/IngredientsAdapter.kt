package com.example.food_recipes.core.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.food_recipes.R
import com.example.food_recipes.core.datamodels.ExtendedIngredient
import com.example.food_recipes.databinding.LayoutIngredientItemBinding
import com.example.food_recipes.misc.Config.Companion.API_IMAGE_URL
import com.example.food_recipes.misc.ViewDiffUtil

class IngredientsAdapter : RecyclerView.Adapter<IngredientsAdapter.MyViewHolder>() {

    private var ingredients = emptyList<ExtendedIngredient>()

    class MyViewHolder(val binding: LayoutIngredientItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = LayoutIngredientItemBinding.inflate(layoutInflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        try{
            if(ingredients[position].image != "")
                holder.binding.recipeIngredientImage.load(
                    API_IMAGE_URL + ingredients[position].image
                ) {
                    crossfade(600)
                    error(R.drawable.ic_baseline_broken_image_24)
                }
            holder.binding.recipeIngredientName.text = ingredients[position].name
            holder.binding.recipeIngredientAmount.text = ingredients[position].amount.toString()
            holder.binding.recipeIngredientUnit.text = ingredients[position].unit
        } catch (_: Exception) {

        }
    }

    override fun getItemCount(): Int {
        return ingredients.size
    }

    fun setIngredients(newIngredients: List<ExtendedIngredient>) {
        val diffUtilResult = DiffUtil.calculateDiff(
            ViewDiffUtil(
                ingredients,
                newIngredients
            )
        )

        ingredients = newIngredients

        diffUtilResult.dispatchUpdatesTo(this)
    }
}