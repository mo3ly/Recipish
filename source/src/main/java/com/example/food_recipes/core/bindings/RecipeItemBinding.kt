package com.example.food_recipes.core.bindings

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import coil.load
import com.example.food_recipes.R
import com.example.food_recipes.core.datamodels.Recipe
import com.example.food_recipes.ui.recipes.RecipesFragmentDirections

class RecipeItemBinding {
    companion object {

        @BindingAdapter("onRecipeClick")
        @JvmStatic
        fun onRecipeClick(recipeRowLayout: View, recipe: Recipe) {
            recipeRowLayout.setOnClickListener {
                try {
                    val action =
                        RecipesFragmentDirections.actionNavigationRecipesToRecipeDetailsActivity(
                            recipe
                        )
                    recipeRowLayout.findNavController().navigate(action)
                } catch (_: java.lang.Exception) {

                }
            }
        }

        @BindingAdapter("loadImageFromUrl")
        @JvmStatic
        fun loadImageFromUrl(imageView: ImageView, imageUrl: String) {
            if (imageUrl.isNotEmpty())
                imageView.load(imageUrl) {
                    crossfade(600)
                    error(R.drawable.ic_baseline_broken_image_24)
                }
        }

        @BindingAdapter("setNumberOfLikes")
        @JvmStatic
        fun setNumberOfLikes(textView: TextView, likes: Int) {
            textView.text = likes.toString()
        }

        @BindingAdapter("getCookingTime")
        @JvmStatic
        fun getCookingTime(textView: TextView, time: Int) {
            textView.text = time.toString()
        }

        @BindingAdapter("getDescription")
        @JvmStatic
        fun getDescription(textView: TextView, description: String) {
            // https://stackoverflow.com/questions/240546/remove-html-tags-from-a-string
            textView.text = HtmlCompat.fromHtml(
                description,
                HtmlCompat.FROM_HTML_MODE_LEGACY
            ).toString()
        }
    }
}